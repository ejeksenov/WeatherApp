package kz.lidercargo.weatherapp.data.di


import kz.lidercargo.weatherapp.data.common.utils.Connectivity
import kz.lidercargo.weatherapp.data.common.utils.ConnectivityImpl
import kz.lidercargo.weatherapp.data.repository.LocationInfoRepositoryImpl
import kz.lidercargo.weatherapp.data.repository.Weather12HourlyForecastRepositoryImpl
import kz.lidercargo.weatherapp.data.repository.Weather5DaysForecastRepositoryImpl
import kz.lidercargo.weatherapp.domain.repository.LocationInfoRepository
import kz.lidercargo.weatherapp.domain.repository.Weather12HourlyForecastRepository
import kz.lidercargo.weatherapp.domain.repository.Weather5DaysForecastRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory<LocationInfoRepository> {  LocationInfoRepositoryImpl(get()) }
    factory<Weather12HourlyForecastRepository> {  Weather12HourlyForecastRepositoryImpl(get()) }
    factory<Weather5DaysForecastRepository> {  Weather5DaysForecastRepositoryImpl(get()) }
    factory<Connectivity> { ConnectivityImpl(androidContext()) }
}