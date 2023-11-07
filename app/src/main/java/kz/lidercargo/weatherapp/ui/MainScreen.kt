package kz.lidercargo.weatherapp.ui

import android.widget.ImageButton
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kz.lidercargo.weatherapp.ui.navigation.Screen
import kz.lidercargo.weatherapp.ui.theme.gradientEnd
import kz.lidercargo.weatherapp.ui.theme.gradientStart
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel = koinViewModel(), navController: NavController) {
    val selectedCities = viewModel.citiesList.observeAsState()
    val pagerState = rememberPagerState {
        selectedCities.value?.filter { it.isSelected }?.size ?: 0
    }
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            viewModel.getLocationInfo(page)
        }
    }
    Scaffold(
        content = {
            HorizontalPager(
                modifier = Modifier
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                gradientStart,
                                gradientEnd
                            )
                        )
                    )
                    .fillMaxSize()
                    .padding(bottom = 56.dp), state = pagerState
            ) {
                WeatherInfoScreen(page = it)
            }
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                containerColor = gradientEnd
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    DotsIndicator(
                        modifier = Modifier
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .align(Alignment.Center),
                        totalDots = pagerState.pageCount,
                        selectedIndex = pagerState.currentPage,
                        selectedColor = Color.White,
                        unSelectedColor = Color.Gray
                    )

                    IconButton(
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                            .align(Alignment.CenterEnd),
                        onClick = {
                            navController.navigate(Screen.CityList.route) {
                                popUpTo(Screen.Main.route) {
                                    inclusive = false
                                }
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = kz.lidercargo.weatherapp.R.drawable.baseline_checklist_rtl_24),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    )
}


@Composable
fun DotsIndicator(
    modifier: Modifier,
    totalDots: Int,
    selectedIndex: Int,
    selectedColor: Color,
    unSelectedColor: Color,
) {
    LazyRow(modifier = modifier) {
        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(selectedColor)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(5.dp)
                        .clip(CircleShape)
                        .background(unSelectedColor)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}