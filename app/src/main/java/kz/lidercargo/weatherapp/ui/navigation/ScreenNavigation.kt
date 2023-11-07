package kz.lidercargo.weatherapp.ui.navigation


sealed class Screen(val route: String) {
    object Main: Screen(route = "main_screen")
    object CityList: Screen(route = "city_list_screen")
    object Weather: Screen(route = "weather_data_screen")
}
