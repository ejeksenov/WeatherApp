package kz.lidercargo.weatherapp.domain.repository

import kz.lidercargo.weatherapp.domain.model.LocationInfo
import kz.lidercargo.weatherapp.domain.model.Result

interface LocationInfoRepository {
    suspend fun getLocationInfo(location: String): Result<LocationInfo>
}