package kz.lidercargo.weatherapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kz.lidercargo.weatherapp.ui.navigation.Screen
import kz.lidercargo.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            WeatherAppTheme {
                NavHost(navController = navController, startDestination = Screen.CityList.route) {
                    composable(Screen.Main.route) {
                        MainScreen(navController = navController)
                    }
                    composable(Screen.CityList.route) {
                        CityListScreen(navController = navController)
                    }
                }
            }
        }
    }
}