package kz.lidercargo.weatherapp.data.repository

import kz.lidercargo.weatherapp.data.networking.Weather12HourlyForecastApi
import kz.lidercargo.weatherapp.data.networking.base.getDataList
import kz.lidercargo.weatherapp.domain.model.Result
import kz.lidercargo.weatherapp.domain.model.Weather12HourlyForecast
import kz.lidercargo.weatherapp.domain.repository.Weather12HourlyForecastRepository

class Weather12HourlyForecastRepositoryImpl(private val weather12HourlyForecastApi: Weather12HourlyForecastApi): BaseRepository<List<Weather12HourlyForecast>>(),
    Weather12HourlyForecastRepository {
    override suspend fun getWeather12HourlyForecastList(locationKey: String): Result<List<Weather12HourlyForecast>> {
        return fetchData(
            dataProvider = {
                weather12HourlyForecastApi.getWeather12HourlyForecast(locationKey).getDataList()
            }
        )
    }
}