package com.bignerdranch.android.weather.core.data.dto

import com.google.gson.annotations.SerializedName


data class SearchCityItemDto(
    @SerializedName("name") val cityName: String,
    @SerializedName("country") val country: String
)

data class LocationShortDto(
    @SerializedName("name") val city: String,
    @SerializedName("country") val country: String,
)

data class CurrentShortDto(
    @SerializedName("temp_c") val tempInCelsius: Double,
    @SerializedName("temp_f") val tempInFahrenheit: Double
)
