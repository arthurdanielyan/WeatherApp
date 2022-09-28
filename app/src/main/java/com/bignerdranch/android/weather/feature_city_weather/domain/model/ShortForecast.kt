package com.bignerdranch.android.weather.feature_city_weather.domain.model


data class ShortForecast(
    val forecastDays: List<ForecastDay>
)

data class ForecastDay(
    var day: String,
    val maxTempInCelsius: Double,
    val minTempInCelsius: Double,
    val maxTempInFahrenheit: Double,
    val minTempInFahrenheit: Double,
    val description: String
)


