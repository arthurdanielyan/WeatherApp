package com.bignerdranch.android.weather.core.data.dto

import com.bignerdranch.android.weather.feature_city_weather.domain.model.CityWeather
import com.google.gson.annotations.SerializedName

fun CityWeatherDto.toCityWeather(): CityWeather =
    CityWeather(
        city = location.city,
        country = location.country,
        tempInCelsius = current.tempInCelsius,
        tempInFahrenheit = current.tempInFahrenheit,
        isDay = current.isDay == 1,
        description = current.condition.description,
        iconLink = current.condition.iconLink,
        pressure = current.pressure
    )

data class CityWeatherDto(
    @SerializedName("location") val location: Location,
    @SerializedName("current") val current: Current
)

data class Location(
    @SerializedName("name") val city: String,
    @SerializedName("country") val country: String,
)

data class Current(
    @SerializedName("temp_c") val tempInCelsius: Double,
    @SerializedName("temp_f") val tempInFahrenheit: Double,
    @SerializedName("is_day") val isDay: Int,
    @SerializedName("condition") val condition: Condition,
    @SerializedName("pressure_mb") val pressure: Int
)

data class Condition(
    @SerializedName("text") val description: String,
    @SerializedName("icon") val iconLink: String
)