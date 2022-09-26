package com.bignerdranch.android.weather.core.data

import com.bignerdranch.android.weather.core.data.dto.CityWeatherDto
import com.bignerdranch.android.weather.core.data.dto.ShortWeatherInfoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("current.json")
    suspend fun getShortWeather(@Query("q") city: String): ShortWeatherInfoDto

    @GET("current.json")
    suspend fun getCityWeather(@Query("q") city: String): CityWeatherDto

    @GET("forecast.json?days=3")
    suspend fun getWeatherOfDay(@Query("q") city: String)
}