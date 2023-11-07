package kz.lidercargo.weatherapp.domain.interaction.hourly

import kz.lidercargo.weatherapp.domain.base.BaseUseCase
import kz.lidercargo.weatherapp.domain.model.Result
import kz.lidercargo.weatherapp.domain.model.Weather12HourlyForecast

interface GetWeather12HourlyForecastUseCase: BaseUseCase<String, List<Weather12HourlyForecast>> {
    override suspend operator fun invoke(param: String): Result<List<Weather12HourlyForecast>>
}