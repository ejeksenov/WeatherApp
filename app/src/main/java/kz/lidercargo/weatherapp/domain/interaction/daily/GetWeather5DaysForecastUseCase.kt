package kz.lidercargo.weatherapp.domain.interaction.daily

import kz.lidercargo.weatherapp.domain.base.BaseUseCase
import kz.lidercargo.weatherapp.domain.model.Weather5DaysForecast
import kz.lidercargo.weatherapp.domain.model.Result

interface GetWeather5DaysForecastUseCase: BaseUseCase<String, Weather5DaysForecast> {
    override suspend fun invoke(param: String): Result<Weather5DaysForecast>
}