package kz.lidercargo.weatherapp.data.networking.model

import com.google.gson.annotations.SerializedName
import kz.lidercargo.weatherapp.data.networking.base.DomainMapper
import kz.lidercargo.weatherapp.domain.model.LocationInfo


data class LocationInfoResponse(@SerializedName("Key") val key: String ?= "Key", @SerializedName("Type") val type: String ?= "", @SerializedName("LocalizedName") val localizedName: String ?= "", @SerializedName("EnglishName") val englishName: String ?= ""): DomainMapper<LocationInfo> {
    override fun mapToDomainModel(): LocationInfo {
        return LocationInfo(key = key.orEmpty(), type = type.orEmpty(), localizedName = localizedName.orEmpty(), englishName = englishName.orEmpty())
    }
}