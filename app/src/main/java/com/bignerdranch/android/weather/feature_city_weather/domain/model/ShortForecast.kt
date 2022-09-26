package com.bignerdranch.android.weather.feature_city_weather.domain.model

import com.bignerdranch.android.weather.core.data.dto.ForecastDays
import com.google.gson.annotations.SerializedName

data class ShortForecast(
    val forecastDays: List<ForecastDay>
)

data class ForecastDay(
    val day: Int,
    val maxTempInCelsius: Double,
    val minTempInCelsius: Double,
    val maxTempInFahrenheit: Double,
    val minTempInFahrenheit: Double,
    val description: String
)


