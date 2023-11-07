package kz.lidercargo.weatherapp.domain.base
import kz.lidercargo.weatherapp.domain.model.Result

interface BaseUseCase<T : Any, R: Any> {
    suspend operator fun invoke(param: T): Result<R>
}