package com.bignerdranch.android.weather.feature_daily_notifications.data.dto

import com.bignerdranch.android.weather.core.data.dto.Current
import com.bignerdranch.android.weather.core.data.dto.ForecastDaysDto
import com.bignerdranch.android.weather.core.data.dto.Location
import com.bignerdranch.android.weather.feature_daily_notifications.domain.model.NotificationWeatherInfo
import com.google.gson.annotations.SerializedName


data class NotificationWeatherInfoDto(
    @SerializedName("location") val location: Location, // only name needed
    @SerializedName("forecast") val forecast: ForecastDaysDto,
    @SerializedName("current") val current: Current
)

fun NotificationWeatherInfoDto.toNotificationWeatherInfo(): NotificationWeatherInfo =
    NotificationWeatherInfo(
        cityName = this.location.city,
        maxTempInCelsius = this.forecast.forecastDays[0].day.maxTempInCelsius,
        minTempInCelsius = this.forecast.forecastDays[0].day.minTempInCelsius,
        maxTempInFahrenheit = this.forecast.forecastDays[0].day.maxTempInFahrenheit,
        minTempInFahrenheit = this.forecast.forecastDays[0].day.minTempInFahrenheit,
        condition = this.current.condition.description
    )