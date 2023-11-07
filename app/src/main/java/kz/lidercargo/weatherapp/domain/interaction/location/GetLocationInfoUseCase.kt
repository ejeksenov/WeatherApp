package kz.lidercargo.weatherapp.domain.interaction.location

import kz.lidercargo.weatherapp.domain.base.BaseUseCase
import kz.lidercargo.weatherapp.domain.model.LocationInfo
import kz.lidercargo.weatherapp.domain.model.Result


interface GetLocationInfoUseCase: BaseUseCase<String, LocationInfo> {
    override suspend operator fun invoke(param: String): Result<LocationInfo>
}