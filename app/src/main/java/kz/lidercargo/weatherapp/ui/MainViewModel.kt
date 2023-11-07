package kz.lidercargo.weatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kz.lidercargo.weatherapp.data.common.utils.Connectivity
import kz.lidercargo.weatherapp.domain.interaction.daily.GetWeather5DaysForecastUseCase
import kz.lidercargo.weatherapp.domain.interaction.hourly.GetWeather12HourlyForecastUseCase
import kz.lidercargo.weatherapp.domain.interaction.location.GetLocationInfoUseCase
import kz.lidercargo.weatherapp.domain.model.*
import kz.lidercargo.weatherapp.ui.base.Loading
import kz.lidercargo.weatherapp.ui.base.ViewState


class MainViewModel(
    private val getLocationInfoUseCase: GetLocationInfoUseCase,
    private val getWeather12HourlyForecastUseCase: GetWeather12HourlyForecastUseCase,
    private val getWeather5DaysForecastUseCase: GetWeather5DaysForecastUseCase,
    private val connectivity: Connectivity
) : ViewModel() {

    private val _citiesList = MutableLiveData<List<CityData>>(cities)
    val citiesList: LiveData<List<CityData>>
        get() = _citiesList

    private val _isReadyButtonEnabled = MutableLiveData<Boolean>(isUserAlreadySelectedCity())
    val isReadyButtonEnabled: LiveData<Boolean>
        get() = _isReadyButtonEnabled

    private val _viewLocationInfo = MutableLiveData<ViewState<LocationInfo>>()
    val viewLocationInfo: LiveData<ViewState<LocationInfo>>
        get() = _viewLocationInfo

    private val _viewWeatherHourlyData = MutableLiveData<ViewState<List<Weather12HourlyForecast>>>()
    val viewWeatherHourlyData: LiveData<ViewState<List<Weather12HourlyForecast>>>
        get() = _viewWeatherHourlyData

    private val _viewWeatherDaily = MutableLiveData<ViewState<Weather5DaysForecast>>()
    val viewWeatherDaily: LiveData<ViewState<Weather5DaysForecast>>
        get() = _viewWeatherDaily

    private val _viewDetailedDay = MutableLiveData<WeatherDayDetailInfo>()
    val viewDetailedDay: LiveData<WeatherDayDetailInfo>
        get() = _viewDetailedDay

    private val detailedDayData = WeatherDayDetailInfo()


    fun onSelectCity(cityIndex: Int, isSelected: Boolean) {
        cities.getOrNull(cityIndex)?.let {
            it.isSelected = isSelected
        }
        _isReadyButtonEnabled.value = isUserAlreadySelectedCity()
    }

    private fun isUserAlreadySelectedCity() = cities.any { it.isSelected }

    fun getLocationInfo(position: Int) {
        _viewLocationInfo.value = Loading()
        _viewWeatherHourlyData.value = Loading()
        _viewWeatherDaily.value = Loading()

        cities.filter { it.isSelected }.getOrNull(position)?.let {
            viewModelScope.launch(Dispatchers.IO) {
                getLocationInfoUseCase.invoke(it.latLon).onSuccess {
                    _viewLocationInfo.postValue(kz.lidercargo.weatherapp.ui.base.Success(it))
                    get12HourlyWeatherInfo(it.key)
                    get5DailyWeatherInfo(it.key)
                }.onFailure {
                    _viewLocationInfo.postValue(kz.lidercargo.weatherapp.ui.base.Error(it.throwable))
                }
            }
        }
    }

    private fun get12HourlyWeatherInfo(locationKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getWeather12HourlyForecastUseCase.invoke(locationKey).onSuccess {
                setDetailedDayHourlyData(it.firstOrNull())
                _viewWeatherHourlyData.postValue(kz.lidercargo.weatherapp.ui.base.Success(it))
            }.onFailure {
                _viewLocationInfo.postValue(kz.lidercargo.weatherapp.ui.base.Error(it.throwable))
            }
        }
    }

    private fun get5DailyWeatherInfo(locationKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getWeather5DaysForecastUseCase.invoke(locationKey).onSuccess {
                setDetailedDailyData(it.dailyForecasts.firstOrNull())
                _viewWeatherDaily.postValue(kz.lidercargo.weatherapp.ui.base.Success(it))
            }.onFailure {
                _viewWeatherDaily.postValue(kz.lidercargo.weatherapp.ui.base.Error(it.throwable))
            }
        }
    }

    private fun setDetailedDayHourlyData(data: Weather12HourlyForecast?) {
        data?.let {
            detailedDayData.relativeHumidity = data.relativeHumidity
            detailedDayData.realFeelTemperature = data.realFeelTemperature
            detailedDayData.uvIndexText = data.UVIndexText
            _viewDetailedDay.postValue(detailedDayData.copy())
        }
    }

    private fun setDetailedDailyData(data: DayForecast?) {
        data?.let {
            detailedDayData.sunRise = data.sunRise
            detailedDayData.sunSet = data.sunSet
            _viewDetailedDay.postValue(detailedDayData.copy())
        }
    }
}