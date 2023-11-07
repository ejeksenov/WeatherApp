package kz.lidercargo.weatherapp.domain.di


import kz.lidercargo.weatherapp.domain.interaction.daily.GetWeather5DaysForecastUseCase
import kz.lidercargo.weatherapp.domain.interaction.daily.GetWeather5DaysForecastUseCaseImpl
import kz.lidercargo.weatherapp.domain.interaction.hourly.GetWeather12HourlyForecastUseCase
import kz.lidercargo.weatherapp.domain.interaction.hourly.GetWeather12HourlyForecastUseCaseImpl
import kz.lidercargo.weatherapp.domain.interaction.location.GetLocationInfoUseCase
import kz.lidercargo.weatherapp.domain.interaction.location.GetLocationInfoUseCaseImpl
import org.koin.dsl.module

val interactionModule = module {
    factory<GetLocationInfoUseCase> {
        GetLocationInfoUseCaseImpl(
            get()
        )
    }
    factory<GetWeather12HourlyForecastUseCase> {
        GetWeather12HourlyForecastUseCaseImpl(
            get()
        )
    }
    factory<GetWeather5DaysForecastUseCase> {
        GetWeather5DaysForecastUseCaseImpl(
            get()
        )
    }
}