package kz.lidercargo.weatherapp.domain.repository

import kz.lidercargo.weatherapp.domain.model.Result
import kz.lidercargo.weatherapp.domain.model.Weather5DaysForecast

interface Weather5DaysForecastRepository {
    suspend fun getWeather5DaysForecastList(locationKey: String): Result<Weather5DaysForecast>
}