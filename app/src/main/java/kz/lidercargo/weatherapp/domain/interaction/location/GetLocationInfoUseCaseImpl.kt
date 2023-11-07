package kz.lidercargo.weatherapp.domain.interaction.location

import kz.lidercargo.weatherapp.domain.interaction.location.GetLocationInfoUseCase
import kz.lidercargo.weatherapp.domain.model.LocationInfo
import kz.lidercargo.weatherapp.domain.model.Result
import kz.lidercargo.weatherapp.domain.repository.LocationInfoRepository

class GetLocationInfoUseCaseImpl(private val locationInfoRepository: LocationInfoRepository):
    GetLocationInfoUseCase {
    override suspend operator fun invoke(param: String): Result<LocationInfo> = locationInfoRepository.getLocationInfo(param)
}