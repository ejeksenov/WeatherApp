package kz.lidercargo.weatherapp.data.repository


import kz.lidercargo.weatherapp.data.networking.LocationInfoApi
import kz.lidercargo.weatherapp.data.networking.base.getData
import kz.lidercargo.weatherapp.domain.model.Result
import kz.lidercargo.weatherapp.domain.repository.LocationInfoRepository
import kz.lidercargo.weatherapp.domain.model.LocationInfo

class LocationInfoRepositoryImpl(private val locationDataApi: LocationInfoApi): BaseRepository<LocationInfo>(), LocationInfoRepository {

    override suspend fun getLocationInfo(location: String): Result<LocationInfo> {
        return fetchData(
            dataProvider = {
                locationDataApi.getLocationData(location = location).getData()
            }
        )
    }


}