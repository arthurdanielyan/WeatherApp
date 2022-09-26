package com.bignerdranch.android.weather.core.data.dto

import com.bignerdranch.android.weather.feature_city_weather.domain.model.ForecastDay
import com.bignerdranch.android.weather.feature_city_weather.domain.model.ShortForecast
import com.google.gson.annotations.SerializedName

fun ShortForecastDto.toShortForecast(): ShortForecast =
    ShortForecast(
        this.forecastDays.forecastDays.map{ it.toForecastDay() }
    )

fun ForecastDayDto.toForecastDay(): ForecastDay =
    ForecastDay(
        day = this.date.substring(this.date.length-2).toInt(),
        maxTempInCelsius = this.day.maxTempInCelsius,
        minTempInCelsius = day.minTempInCelsius,
        maxTempInFahrenheit = day.maxTempInFahrenheit,
        minTempInFahrenheit = day.minTempInFahrenheit,
        description = day.condition.description
    )

data class ShortForecastDto(
    @SerializedName("forecast") val forecastDays: ForecastDays
)

data class ForecastDays(
    @SerializedName("forecastday") val forecastDays: List<ForecastDayDto>
)

data class ForecastDayDto(
    @SerializedName("date") val date: String,
    @SerializedName("day") val day: Day,
)

data class Day(
    @SerializedName("maxtemp_c") val maxTempInCelsius: Double,
    @SerializedName("mintemp_c") val minTempInCelsius: Double,
    @SerializedName("maxtemp_f") val maxTempInFahrenheit: Double,
    @SerializedName("mintemp_f") val minTempInFahrenheit: Double,
    @SerializedName("condition") val condition: Condition
)