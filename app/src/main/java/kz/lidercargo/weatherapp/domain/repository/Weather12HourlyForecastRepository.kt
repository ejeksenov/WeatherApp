package kz.lidercargo.weatherapp.domain.repository

import kz.lidercargo.weatherapp.domain.model.Result
import kz.lidercargo.weatherapp.domain.model.Weather12HourlyForecast

interface Weather12HourlyForecastRepository {
    suspend fun getWeather12HourlyForecastList(locationKey: String): Result<List<Weather12HourlyForecast>>
}