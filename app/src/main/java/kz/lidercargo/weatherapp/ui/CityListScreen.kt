package kz.lidercargo.weatherapp.ui

import android.app.Activity
import android.graphics.Color
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import kz.lidercargo.weatherapp.R
import kz.lidercargo.weatherapp.domain.model.CityData
import kz.lidercargo.weatherapp.ui.navigation.Screen
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityListScreen(viewModel: MainViewModel = koinViewModel(), navController: NavController) {
    val cityList = viewModel.citiesList.observeAsState()
    val activity = (LocalContext.current as? Activity)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth().padding(end = 16.dp)) {
                        Text(
                            modifier = Modifier.wrapContentWidth().align(Alignment.CenterStart),
                            text = "Select cities",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                        IconButton(
                            modifier = Modifier
                                .height(36.dp)
                                .width(36.dp)
                                .align(Alignment.CenterEnd),
                            onClick = {
                                activity?.onBackPressed()
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_close_24),
                                contentDescription = ""
                            )
                        }
                    }
                }
            )
        },
        content = {
            LazyColumn(modifier = Modifier.padding(vertical = 56.dp)) {
                item(cityList.value?.size) {
                    cityList.value?.forEachIndexed { index, cityData ->
                        CityItem(cityData = cityData, onChecked = {
                            viewModel.onSelectCity(index, it)
                        })
                        HorizontalDivider()
                    }
                }
            }
        },
        bottomBar = {
            CityListBottomBar(viewModel = viewModel, navController = navController)
        }
    )
}

@Composable
fun CityListBottomBar(viewModel: MainViewModel, navController: NavController) {
    val isEnabledState = viewModel.isReadyButtonEnabled.observeAsState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .height(48.dp)
                .align(Alignment.Center),
            enabled = isEnabledState.value ?: false,
            onClick = {
                navController.navigate(Screen.Main.route) {
                    popUpTo(Screen.CityList.route) {
                        inclusive = true
                    }
                }
            }) {
            Text(
                modifier = Modifier.fillMaxSize(),
                text = "Ready",
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun CityItem(
    cityData: CityData,
    onChecked: (Boolean) -> Unit
) {
    val checked = remember { mutableStateOf(cityData.isSelected) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp)
            .clickable {
                checked.value = !cityData.isSelected
                onChecked.invoke(!cityData.isSelected)
            }
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterStart),
            text = cityData.name,
            fontSize = 16.sp
        )
        Checkbox(
            modifier = Modifier.align(Alignment.CenterEnd),
            checked = checked.value,
            onCheckedChange = {
                checked.value = it
                onChecked.invoke(it)
            })
    }

}