package kz.lidercargo.weatherapp

import android.app.Application
import kz.lidercargo.weatherapp.data.di.networkingModule
import kz.lidercargo.weatherapp.data.di.repositoryModule
import kz.lidercargo.weatherapp.domain.di.interactionModule
import kz.lidercargo.weatherapp.ui.di.appModule
import kz.lidercargo.weatherapp.ui.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    companion object {
        lateinit var instance: Application
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        startKoin {
            androidContext(this@App)
            if (BuildConfig.DEBUG) androidLogger(Level.DEBUG)
            modules(appModules + domainModules + dataModules)
        }
    }
}


val appModules = listOf(viewModelModule, appModule)
val domainModules = listOf(interactionModule)
val dataModules = listOf(networkingModule, repositoryModule)