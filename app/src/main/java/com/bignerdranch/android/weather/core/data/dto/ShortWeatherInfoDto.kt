package com.bignerdranch.android.weather.core.data.dto

import com.bignerdranch.android.weather.feature_search_city.data.model.ShortWeatherInfo
import com.google.gson.annotations.SerializedName

fun ShortWeatherInfoDto.toShortWeatherInfo(): ShortWeatherInfo =
    ShortWeatherInfo(
        city = location.city,
        country = location.country,
        tempInCelsius = current.tempInCelsius,
        tempInFahrenheit = current.tempInFahrenheit
    )

data class ShortWeatherInfoDto(
    @SerializedName("location") val location: LocationShortDto,
    @SerializedName("current") val current: CurrentShortDto
)

data class LocationShortDto(
    @SerializedName("name") val city: String,
    @SerializedName("country") val country: String,
)

data class CurrentShortDto(
    @SerializedName("temp_c") val tempInCelsius: Double,
    @SerializedName("temp_f") val tempInFahrenheit: Double
)
