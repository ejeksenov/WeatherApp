package kz.lidercargo.weatherapp.data.di

import kz.lidercargo.weatherapp.BuildConfig
import kz.lidercargo.weatherapp.data.networking.LocationInfoApi
import kz.lidercargo.weatherapp.data.networking.Weather12HourlyForecastApi
import kz.lidercargo.weatherapp.data.networking.Weather5DaysForecastApi
import kz.lidercargo.weatherapp.data.networking.interceptor.CacheInterceptor
import kz.lidercargo.weatherapp.data.networking.interceptor.ForceCacheInterceptor
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://dataservice.accuweather.com/"
const val API_KEY = "J8l7kWdGBm8k5igTWhesVAcSNBblxm4o"

val networkingModule = module {
    single { GsonConverterFactory.create() as Converter.Factory }
    single { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) as Interceptor }
    single {
        OkHttpClient.Builder().apply {
            cache(Cache(File(androidApplication().applicationContext.cacheDir, "http-cache"), 10L * 1024L * 1024L))
            if (BuildConfig.DEBUG) addInterceptor(get())
                .callTimeout(10, TimeUnit.SECONDS)
            addNetworkInterceptor(CacheInterceptor())
            addInterceptor(ForceCacheInterceptor(get()))
        }.build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(get())
            .build()
    }
    single {
        get<Retrofit>().create(LocationInfoApi::class.java)
    }
    single {
        get<Retrofit>().create(Weather12HourlyForecastApi::class.java)
    }
    single {
        get<Retrofit>().create(Weather5DaysForecastApi::class.java)
    }
}