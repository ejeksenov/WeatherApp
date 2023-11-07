package kz.lidercargo.weatherapp.data.networking.model

import com.google.gson.annotations.SerializedName
import kz.lidercargo.weatherapp.data.common.utils.ChangeDateFormat.changeDateFormatToTime
import kz.lidercargo.weatherapp.data.common.utils.ChangeImageFormat.getImageFormat
import kz.lidercargo.weatherapp.data.networking.base.DomainMapper
import kz.lidercargo.weatherapp.domain.model.Weather12HourlyForecast
import kotlin.math.roundToInt

data class Weather12HourlyForecastResponse(@SerializedName("DateTime") val dateTime: String, @SerializedName("WeatherIcon") val weatherIcon : Int ?= 0, @SerializedName("IconPhrase") val iconPhrase: String ?= "",
                                           @SerializedName("Temperature") val temperature: TemperatureResponse, @SerializedName("RealFeelTemperature") val realFeelTemperature: TemperatureResponse, @SerializedName("Wind") val wind: WindResponse,
                                           @SerializedName("RelativeHumidity") val relativeHumidity: Int, @SerializedName("UVIndexText") val uviIndexText: String, @SerializedName("MobileLink") val mobileLink: String): DomainMapper<Weather12HourlyForecast> {
    override fun mapToDomainModel(): Weather12HourlyForecast {
        return Weather12HourlyForecast(
            changeDateFormatToTime(dateTime), getImageFormat(weatherIcon ?: 0), iconPhrase ?: "",
            "${temperature.value.roundToInt()}°", "${realFeelTemperature.value.roundToInt()}°", "${wind.speed.value} ${wind.speed.unit}",
            "$relativeHumidity%", uviIndexText ?: "", mobileLink ?: "")
    }
}

data class TemperatureResponse(@SerializedName("Value") val value: Double, @SerializedName("Unit") val unit: String, @SerializedName("UnitType") val unitType: Int)
data class WindResponse(@SerializedName("Speed") val speed: SpeedResponse, @SerializedName("Direction") val direction: DirectionResponse)
data class SpeedResponse(@SerializedName("Value") val value: Double, @SerializedName("Unit") val unit: String, @SerializedName("UnitType") val unitType: Int)
data class DirectionResponse(@SerializedName("Degrees") val degrees: Int, @SerializedName("Localized") val localized: String, @SerializedName("English") val english: String)