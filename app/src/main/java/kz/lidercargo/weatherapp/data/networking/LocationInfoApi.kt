package kz.lidercargo.weatherapp.data.networking

import kz.lidercargo.weatherapp.data.di.API_KEY
import kz.lidercargo.weatherapp.data.networking.model.LocationInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationInfoApi {

    @GET("locations/v1/cities/geoposition/search")
    suspend fun getLocationData(@Query("apikey") apiKey: String = API_KEY, @Query("q") location: String): Response<LocationInfoResponse>

}