package com.bignerdranch.android.weather.feature_city_weather.domain.repository

import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.core.model.WeatherInfoList
import com.bignerdranch.android.weather.feature_city_weather.domain.model.CityWeather
import com.bignerdranch.android.weather.feature_city_weather.domain.model.HourForecast
import com.bignerdranch.android.weather.core.data.model.ShortWeatherInfo
import kotlinx.coroutines.flow.Flow

interface CityWeatherRepository {

    suspend fun getWeather(city: String): Flow<Result<CityWeather>>

    suspend fun get3DaysForecast(city: String): Flow<Result<WeatherInfoList>>

    suspend fun saveCity(city: ShortWeatherInfo)

    suspend fun getHourlyForecast(city: String): Flow<Result<List<HourForecast>>>
}