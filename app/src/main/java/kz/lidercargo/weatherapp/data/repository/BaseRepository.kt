package kz.lidercargo.weatherapp.data.repository

import kotlinx.coroutines.withContext
import kz.lidercargo.weatherapp.data.common.coroutine.CoroutineContextProvider
import kz.lidercargo.weatherapp.data.common.utils.Connectivity
import kz.lidercargo.weatherapp.data.networking.GENERAL_NETWORK_ERROR
import kz.lidercargo.weatherapp.domain.model.Failure
import kz.lidercargo.weatherapp.domain.model.HttpError
import kz.lidercargo.weatherapp.domain.model.Result
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class BaseRepository<T : Any> : KoinComponent {
  private val connectivity: Connectivity by inject()
  private val contextProvider: CoroutineContextProvider by inject()


  protected suspend fun fetchData(dataProvider: suspend () -> Result<T>): Result<T> {
    return if (connectivity.hasNetworkAccess()) {
      withContext(contextProvider.io) {
        dataProvider()
      }
    } else {
      Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
    }
  }

  protected suspend fun fetchDataList(
    apiDataProvider: suspend () -> Result<List<T>>
  ): Result<List<T>> {
    return if (connectivity.hasNetworkAccess()) {
      withContext(contextProvider.io) {
        apiDataProvider()
      }
    } else {
      Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
    }
  }
}