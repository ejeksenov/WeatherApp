package kz.lidercargo.weatherapp.domain.model

data class WeatherDayDetailInfo(
    var sunRise: String = "",
    var sunSet: String = "",
    var relativeHumidity: String = "",
    var realFeelTemperature: String = "",
    var uvIndexText: String = ""
)
