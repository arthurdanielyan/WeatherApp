package com.bignerdranch.android.weather.core.data.dto.mapper

import com.bignerdranch.android.weather.core.data.dto.CityWeatherDto
import com.bignerdranch.android.weather.core.data.dto.ForecastDayDto
import com.bignerdranch.android.weather.core.data.dto.HourDto
import com.bignerdranch.android.weather.core.data.dto.ForecastDto
import com.bignerdranch.android.weather.core.model.Date
import com.bignerdranch.android.weather.feature_city_weather.domain.model.CityWeather
import com.bignerdranch.android.weather.core.model.WeatherInfo
import com.bignerdranch.android.weather.core.model.WeatherInfoList
import com.bignerdranch.android.weather.feature_city_weather.domain.model.HourForecast

fun CityWeatherDto.toCityWeather(): CityWeather =
    CityWeather(
        city = location.city,
        country = location.country,
        tempInCelsius = current.tempInCelsius,
        tempInFahrenheit = current.tempInFahrenheit,
        isDay = current.isDay == 1,
        description = current.condition.description,
        iconUrl = current.condition.iconUrl,
        icon = null,
        pressure = current.pressure
    )

//2022-10-28"
fun ForecastDayDto.toForecastDay(): WeatherInfo =
    WeatherInfo(
        date = Date(
            day = this.date.substring(this.date.length-2).toInt(),
            month = this.date.substring(this.date.length-5, 7).toInt(),
            year = this.date.substring(0, 4).toInt(),
        ),
        dayName = "",
        maxTempInCelsius = this.day.maxTempInCelsius,
        minTempInCelsius = day.minTempInCelsius,
        maxTempInFahrenheit = day.maxTempInFahrenheit,
        minTempInFahrenheit = day.minTempInFahrenheit,
        description = day.condition.description,
        iconUrl = day.condition.iconUrl
    )

fun ForecastDto.toShortForecast(): WeatherInfoList =
    WeatherInfoList(
        this.forecastDays.forecastDays.map { it.toForecastDay() }
    )

fun HourDto.toHourForecast(): HourForecast =
    HourForecast(
        time = this.time.substringAfter(' '),
        date = Date( // from api date comes in a format of YYYY-MM-DD HH:MM
            day = this.time.substringAfterLast('-').substringBefore(' ').toInt(),
            month = this.time.substringAfter('-').substringBefore('-').toInt(),
            year = this.time.substringBefore('-').toInt(),
        ),
        tempInCelsius = this.tempCelsius,
        tempInFahrenheit = this.tempFahrenheit,
        iconUrl = this.condition.iconUrl,
        icon = null
    )