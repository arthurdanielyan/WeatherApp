package com.bignerdranch.android.weather.core.data.dto

import com.google.gson.annotations.SerializedName

data class ShortForecastDto(
    @SerializedName("forecast") val forecastDays: ForecastDaysDto
)

data class ForecastDaysDto(
    @SerializedName("forecastday") val forecastDays: List<ForecastDayDto>
)

data class ForecastDayDto(
    @SerializedName("date") val date: String,
    @SerializedName("day") val day: DayDto,
)

data class DayDto(
    @SerializedName("maxtemp_c") val maxTempInCelsius: Double,
    @SerializedName("mintemp_c") val minTempInCelsius: Double,
    @SerializedName("maxtemp_f") val maxTempInFahrenheit: Double,
    @SerializedName("mintemp_f") val minTempInFahrenheit: Double,
    @SerializedName("condition") val condition: ConditionDto
)