package com.bignerdranch.android.weather.core.data.dto

import com.google.gson.annotations.SerializedName

data class ForecastDto(
    @SerializedName("forecast") val forecastDays: ForecastDaysDto
)

data class ForecastDaysDto(
    @SerializedName("forecastday") val forecastDays: List<ForecastDayDto>
)

data class ForecastDayDto(
    @SerializedName("date") val date: String,
    @SerializedName("day") val day: DayDto,
    @SerializedName("hour") val hours: List<HourDto>
)

data class DayDto(
    @SerializedName("maxtemp_c") val maxTempInCelsius: Double,
    @SerializedName("mintemp_c") val minTempInCelsius: Double,
    @SerializedName("maxtemp_f") val maxTempInFahrenheit: Double,
    @SerializedName("mintemp_f") val minTempInFahrenheit: Double,
    @SerializedName("condition") val condition: ConditionDto
)

data class HourDto(
    @SerializedName("time") val time: String,
    @SerializedName("temp_c") val tempCelsius: Double,
    @SerializedName("temp_f") val tempFahrenheit: Double,
    @SerializedName("condition") val condition: ConditionDto
)