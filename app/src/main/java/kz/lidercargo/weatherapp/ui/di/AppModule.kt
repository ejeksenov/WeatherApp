package kz.lidercargo.weatherapp.ui.di

import kz.lidercargo.weatherapp.data.common.coroutine.CoroutineContextProvider
import kz.lidercargo.weatherapp.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { CoroutineContextProvider() }
}

val viewModelModule = module {
    viewModel {
        MainViewModel(get(), get(), get(), get())
    }
}