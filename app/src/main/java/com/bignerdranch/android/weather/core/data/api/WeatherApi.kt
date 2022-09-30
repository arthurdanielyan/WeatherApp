package com.bignerdranch.android.weather.core.data.api

import com.bignerdranch.android.weather.core.data.dto.CityWeatherDto
import com.bignerdranch.android.weather.core.data.dto.ShortForecastDto
import com.bignerdranch.android.weather.core.data.dto.ShortWeatherInfoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("current.json")
    suspend fun getShortWeather(@Query("q") city: String): ShortWeatherInfoDto

    @GET("current.json")
    suspend fun getCityWeather(@Query("q") city: String): CityWeatherDto

    @GET("forecast.json")
    suspend fun getForecast(@Query("q") city: String, @Query("days") days: Int = 3): ShortForecastDto
}