package kz.lidercargo.weatherapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import kotlinx.coroutines.launch
import kz.lidercargo.weatherapp.R
import kz.lidercargo.weatherapp.domain.model.DayForecast
import kz.lidercargo.weatherapp.domain.model.Weather12HourlyForecast
import kz.lidercargo.weatherapp.domain.model.Weather5DaysForecast
import kz.lidercargo.weatherapp.domain.model.cities
import kz.lidercargo.weatherapp.ui.base.Error
import kz.lidercargo.weatherapp.ui.base.NoInternetState
import kz.lidercargo.weatherapp.ui.base.Success
import kz.lidercargo.weatherapp.ui.theme.gradientEnd
import kz.lidercargo.weatherapp.ui.theme.gradientStart
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun WeatherInfoScreen(
    viewModel: MainViewModel = koinViewModel(),
    page: Int
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = cities.filter { it.isSelected }.getOrNull(page)?.name.orEmpty(),
                        fontSize = 28.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.topAppBarColors(containerColor = gradientStart)
            )
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            gradientStart,
                            gradientEnd
                        )
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            val lazyListState = rememberLazyListState()
            var scrolledY = 0f
            var previousOffset = 0
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState,
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 64.dp)
                            .graphicsLayer {
                                scrolledY += lazyListState.firstVisibleItemScrollOffset - previousOffset
                                translationY = scrolledY * 0.5f
                                previousOffset = lazyListState.firstVisibleItemScrollOffset
                            }
                    ) {
                        Hourly12View(viewModel = viewModel) {
                            scope.launch {
                                snackbarHostState.showSnackbar(it)
                            }
                        }
                        Daily5View(viewModel = viewModel) {
                            scope.launch {
                                snackbarHostState.showSnackbar(it)
                            }
                        }
                        WeatherDayDetailInfoView(viewModel)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Hourly12View(viewModel: MainViewModel, onShowSnackBar: (String) -> Unit) {
    val weatherHourlyInfoState = viewModel.viewWeatherHourlyData.observeAsState()
    when (weatherHourlyInfoState.value) {
        is Error -> onShowSnackBar.invoke((weatherHourlyInfoState.value as? Error<List<Weather12HourlyForecast>>)?.error?.localizedMessage.orEmpty())
        is NoInternetState -> onShowSnackBar.invoke(
            ContextCompat.getString(
                LocalContext.current,
                R.string.no_internet_error_message
            )
        )
        is Success -> {
            (weatherHourlyInfoState.value as? Success<List<Weather12HourlyForecast>>)?.data?.let { list ->
                val nearestHourlyWeatherData = list.first()
                Column {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        text = nearestHourlyWeatherData.iconPhrase,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    ) {
                        GlideImage(
                            modifier = Modifier
                                .height(50.dp)
                                .width(50.dp)
                                .align(Alignment.CenterVertically),
                            model = nearestHourlyWeatherData.weatherIcon,
                            contentDescription = ""
                        )
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = nearestHourlyWeatherData.temperature,
                            fontSize = 80.sp,
                            color = Color.White
                        )
                    }
                    Divider()
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        text = ContextCompat.getString(LocalContext.current, R.string.hourly)
                            .uppercase(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                    LazyRow(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        content = {
                            for (data in list) {
                                item {
                                    WeatherHourlyInfoItem(data)
                                    if (data != list.lastOrNull()) {
                                        VerticalDivider(
                                            modifier = Modifier.height(154.dp),
                                            color = Color.White.copy(alpha = 0.5f)
                                        )
                                    }
                                }
                            }
                        })
                }
            }
        }
        else -> {
            ShowLoadingView()
        }
    }

}


@Composable
fun Daily5View(viewModel: MainViewModel, onShowSnackBar: (String) -> Unit) {
    val weatherDailyInfoState = viewModel.viewWeatherDaily.observeAsState()
    when (weatherDailyInfoState.value) {
        is Error -> onShowSnackBar.invoke((weatherDailyInfoState.value as? Error<Weather5DaysForecast>)?.error?.localizedMessage.orEmpty())
        is NoInternetState -> onShowSnackBar.invoke(
            ContextCompat.getString(
                LocalContext.current,
                R.string.no_internet_error_message
            )
        )
        is Success -> {
            (weatherDailyInfoState.value as? Success<Weather5DaysForecast>)?.data?.let { data ->
                Column {
                    Divider()
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        text = ContextCompat.getString(LocalContext.current, R.string.daily)
                            .uppercase(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                    LazyRow(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        content = {
                            data.dailyForecasts.forEach {
                                item {
                                    WeatherDailyInfoItem(data = it)
                                    if (it != data.dailyForecasts.lastOrNull()) {
                                        VerticalDivider(
                                            modifier = Modifier.height(216.dp),
                                            color = Color.White.copy(alpha = 0.5f)
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            }
        }
        else -> {
            ShowLoadingView()
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WeatherDailyInfoItem(data: DayForecast) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(80.dp)
            .padding(horizontal = 8.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .height(20.dp)
                .wrapContentWidth(),
            text = data.date.uppercase(),
            color = Color.White
        )

        GlideImage(
            modifier = Modifier
                .height(46.dp)
                .width(30.dp)
                .padding(top = 16.dp),
            model = data.dayIcon,
            contentDescription = ""
        )

        Text(
            modifier = Modifier
                .height(36.dp)
                .wrapContentWidth()
                .padding(top = 16.dp),
            text = data.temperatureMaximum,
            color = Color.White
        )

        GlideImage(
            modifier = Modifier
                .height(46.dp)
                .width(30.dp)
                .padding(top = 16.dp),
            model = data.nightIcon,
            contentDescription = ""
        )

        Text(
            modifier = Modifier
                .height(36.dp)
                .wrapContentWidth()
                .padding(top = 16.dp),
            text = data.temperatureMinimum,
            color = Color.White
        )
    }
}

@Composable
fun ShowLoadingView() {
    Box(
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp)
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.surfaceVariant,
            trackColor = MaterialTheme.colorScheme.secondary,
        )
    }
}

@Composable
fun Divider() = HorizontalDivider(modifier = Modifier.padding(16.dp), color = Color.White)


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun WeatherHourlyInfoItem(data: Weather12HourlyForecast) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(72.dp)
            .padding(horizontal = 8.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .height(20.dp)
                .wrapContentWidth(),
            text = data.dateTime,
            color = Color.White
        )
        GlideImage(
            modifier = Modifier
                .height(46.dp)
                .width(30.dp)
                .padding(top = 16.dp),
            model = data.weatherIcon,
            contentDescription = ""
        )
        Row(
            modifier = Modifier
                .height(36.dp)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .height(20.dp)
                    .wrapContentWidth(),
                painter = painterResource(R.drawable.ic_drop),
                contentDescription = ""
            )
            Text(
                modifier = Modifier
                    .height(20.dp)
                    .wrapContentWidth()
                    .padding(start = 4.dp),
                text = data.relativeHumidity,
                color = Color.LightGray
            )
        }
        Text(
            modifier = Modifier
                .height(20.dp)
                .wrapContentWidth(),
            text = data.temperature,
            color = Color.White
        )
    }
}

@Composable
fun WeatherDayDetailInfoView(viewModel: MainViewModel) {
    val detailDataState = viewModel.viewDetailedDay.observeAsState()
    Column {
        Divider()
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            text = ContextCompat.getString(LocalContext.current, R.string.detailed)
                .uppercase(),
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 18.sp
        )
        LazyColumn(
            modifier = Modifier
                .height(273.dp)
                .padding(vertical = 8.dp),
            content = {
                items(5) {
                    WeatherDayDetailItem(
                        first = ContextCompat.getString(
                            LocalContext.current,
                            when (it) {
                                0 -> R.string.realFeelTemperature
                                1 -> R.string.sunSet
                                2 -> R.string.sunRise
                                3 -> R.string.humidity
                                else -> R.string.uvIndex
                            }
                        ),
                        second = when (it) {
                            0 -> detailDataState.value?.realFeelTemperature.orEmpty()
                            1 -> detailDataState.value?.sunSet.orEmpty()
                            2 -> detailDataState.value?.sunRise.orEmpty()
                            3 -> detailDataState.value?.relativeHumidity.orEmpty()
                            else -> detailDataState.value?.uvIndexText.orEmpty()
                        }
                    )
                }
            }
        )
    }
}

@Composable
fun WeatherDayDetailItem(first: String, second: String) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .height(20.dp)
                    .wrapContentWidth()
                    .align(Alignment.CenterStart),
                text = first,
                color = Color.White,
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier
                    .height(20.dp)
                    .wrapContentWidth()
                    .align(Alignment.CenterEnd),
                text = second,
                color = Color.White,
                fontSize = 16.sp
            )
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color.White)
    }
}
