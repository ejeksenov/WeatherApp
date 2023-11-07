package kz.lidercargo.weatherapp.domain.interaction.daily

import kz.lidercargo.weatherapp.domain.interaction.daily.GetWeather5DaysForecastUseCase
import kz.lidercargo.weatherapp.domain.model.Result
import kz.lidercargo.weatherapp.domain.model.Weather5DaysForecast
import kz.lidercargo.weatherapp.domain.repository.Weather5DaysForecastRepository

class GetWeather5DaysForecastUseCaseImpl(private val weather5DaysForecastRepository: Weather5DaysForecastRepository):
    GetWeather5DaysForecastUseCase {
    override suspend fun invoke(param: String): Result<Weather5DaysForecast> = weather5DaysForecastRepository.getWeather5DaysForecastList(param)
}