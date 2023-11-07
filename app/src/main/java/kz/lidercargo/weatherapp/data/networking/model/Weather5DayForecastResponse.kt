package kz.lidercargo.weatherapp.data.networking.model

import com.google.gson.annotations.SerializedName
import kz.lidercargo.weatherapp.data.common.utils.ChangeDateFormat.changeDateFormatToDate
import kz.lidercargo.weatherapp.data.common.utils.ChangeDateFormat.changeDateFormatToTime
import kz.lidercargo.weatherapp.data.common.utils.ChangeImageFormat.getImageFormat
import kz.lidercargo.weatherapp.data.networking.base.DomainMapper
import kz.lidercargo.weatherapp.domain.model.DayForecast
import kz.lidercargo.weatherapp.domain.model.Weather5DaysForecast
import kotlin.math.roundToInt

data class Weather5DayForecastResponse(@SerializedName("DailyForecasts") val dailyForeCasts: List<DayForecastResponse>): DomainMapper<Weather5DaysForecast> {
    override fun mapToDomainModel(): Weather5DaysForecast {
        return Weather5DaysForecast(dailyForeCasts.map {
            DayForecast(
                date = changeDateFormatToDate(it.date),
                sunRise = changeDateFormatToTime(it.sun.rise),
                sunSet = changeDateFormatToTime(it.sun.set),
                temperatureMaximum = "${it.temperature.maximum.value.roundToInt()}°",
                temperatureMinimum = "${it.temperature.minimum.value.roundToInt()}°",
                dayIcon = getImageFormat(it.day.icon),
                nightIcon = getImageFormat(it.night.icon),
                mobileLink = it.mobileLink
            )
        })
    }
}

data class DayForecastResponse(@SerializedName("Date") val date: String, @SerializedName("Sun") val sun: SunResponse,
                       @SerializedName("Temperature") val temperature: DayTemperatureResponse, @SerializedName("Day") val day: DayResponse,
                       @SerializedName("Night") val night: DayResponse, @SerializedName("MobileLink") val mobileLink: String)

data class SunResponse(@SerializedName("Rise") val rise: String, @SerializedName("Set") val set: String)

data class DayTemperatureResponse(@SerializedName("Maximum") val maximum: MaximumResponse, @SerializedName("Minimum") val minimum: MaximumResponse)

data class MaximumResponse(@SerializedName("Value") val value: Double, @SerializedName("Unit") val unit: String)

data class DayResponse(@SerializedName("Icon") val icon: Int)