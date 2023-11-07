package kz.lidercargo.weatherapp.data.networking.base

import kz.lidercargo.weatherapp.data.networking.GENERAL_NETWORK_ERROR
import kz.lidercargo.weatherapp.domain.model.Failure
import kz.lidercargo.weatherapp.domain.model.HttpError
import kz.lidercargo.weatherapp.domain.model.Result
import kz.lidercargo.weatherapp.domain.model.Success
import retrofit2.Response
import java.io.IOException

interface DomainMapper<T : Any> {
  fun mapToDomainModel(): T
}

inline fun <T : Any> Response<T>.onSuccess(action: (T) -> Unit): Response<T> {
  if (isSuccessful) body()?.run(action)
  return this
}

inline fun <T : Any> Response<T>.onFailure(action: (HttpError) -> Unit) {
  if (!isSuccessful) errorBody()?.run { action(HttpError(Throwable(message()), code())) }
}

inline fun <T : Any> Response<List<T>>.onSuccessList(action: (List<T>) -> Unit): Response<List<T>> {
  if (isSuccessful) body()?.run(action)
  return this
}

inline fun <T : Any> Response<List<T>>.onFailureList(action: (HttpError) -> Unit) {
  if (!isSuccessful) errorBody()?.run { action(HttpError(Throwable(message()), code())) }
}

/**
 * Use this when communicating only with the api service
 */
inline fun < T : DomainMapper<R>, R : Any> Response<T>.getData(): Result<R> {
  try {
    onSuccess { return Success(it.mapToDomainModel()) }
    onFailure { return Failure(it) }
    return Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
  } catch (e: IOException) {
    return Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
  }
}

inline fun <T : DomainMapper<R>, R : Any> Response<List<T>>.getDataList(): Result<List<R>> {
  try {
    onSuccessList {
      return Success(it.map { it1 -> it1.mapToDomainModel() })
    }
    onFailureList {
      return Failure(it)
    }
    return Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
  } catch (e: IOException) {
    return Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
  }
}
