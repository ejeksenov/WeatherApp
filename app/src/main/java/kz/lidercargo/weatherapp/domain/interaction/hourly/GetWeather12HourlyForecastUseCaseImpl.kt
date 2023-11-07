package kz.lidercargo.weatherapp.domain.interaction.hourly

import kz.lidercargo.weatherapp.domain.interaction.hourly.GetWeather12HourlyForecastUseCase
import kz.lidercargo.weatherapp.domain.model.Result
import kz.lidercargo.weatherapp.domain.model.Weather12HourlyForecast
import kz.lidercargo.weatherapp.domain.repository.Weather12HourlyForecastRepository

class GetWeather12HourlyForecastUseCaseImpl(private val weather12HourlyForecastRepository: Weather12HourlyForecastRepository):
    GetWeather12HourlyForecastUseCase {
    override suspend fun invoke(param: String): Result<List<Weather12HourlyForecast>> = weather12HourlyForecastRepository.getWeather12HourlyForecastList(param)
}