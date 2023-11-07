package kz.lidercargo.weatherapp.data.repository

import kz.lidercargo.weatherapp.data.networking.Weather5DaysForecastApi
import kz.lidercargo.weatherapp.data.networking.base.getData
import kz.lidercargo.weatherapp.domain.model.Result
import kz.lidercargo.weatherapp.domain.model.Weather5DaysForecast
import kz.lidercargo.weatherapp.domain.repository.Weather5DaysForecastRepository

class Weather5DaysForecastRepositoryImpl(private val weather5DaysForecastApi: Weather5DaysForecastApi): BaseRepository<Weather5DaysForecast>(), Weather5DaysForecastRepository {
    override suspend fun getWeather5DaysForecastList(locationKey: String): Result<Weather5DaysForecast> {
        return fetchData(
            dataProvider = {
                weather5DaysForecastApi.getWeather5DaysForecast(locationKey).getData()
            }
        )
    }

}