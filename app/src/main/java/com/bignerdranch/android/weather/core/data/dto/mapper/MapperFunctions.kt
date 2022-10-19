package com.bignerdranch.android.weather.core.data.dto.mapper

import com.bignerdranch.android.weather.core.data.dto.CityWeatherDto
import com.bignerdranch.android.weather.core.data.dto.ForecastDayDto
import com.bignerdranch.android.weather.core.data.dto.ShortForecastDto
import com.bignerdranch.android.weather.feature_city_weather.domain.model.CityWeather
import com.bignerdranch.android.weather.core.model.ForecastDay
import com.bignerdranch.android.weather.core.model.ShortForecastList

fun CityWeatherDto.toCityWeather(): CityWeather =
    CityWeather(
        city = location.city,
        country = location.country,
        tempInCelsius = current.tempInCelsius,
        tempInFahrenheit = current.tempInFahrenheit,
        isDay = current.isDay == 1,
        description = current.condition.description,
        icon = null,
        pressure = current.pressure
    )

fun ForecastDayDto.toForecastDay(): ForecastDay =
    ForecastDay(
        date = this.date.substring(this.date.length - 2),
        maxTempInCelsius = this.day.maxTempInCelsius,
        minTempInCelsius = day.minTempInCelsius,
        maxTempInFahrenheit = day.maxTempInFahrenheit,
        minTempInFahrenheit = day.minTempInFahrenheit,
        description = day.condition.description
    )

fun ShortForecastDto.toShortForecast(): ShortForecastList =
    ShortForecastList(
        this.forecastDays.forecastDays.map { it.toForecastDay() }
    )