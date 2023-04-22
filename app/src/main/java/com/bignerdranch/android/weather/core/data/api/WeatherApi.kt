package com.bignerdranch.android.weather.core.data.api

import com.bignerdranch.android.weather.core.data.dto.CityWeatherDto
import com.bignerdranch.android.weather.core.data.dto.ForecastDto
import com.bignerdranch.android.weather.core.data.dto.SearchCityItemDto
import com.bignerdranch.android.weather.feature_daily_notifications.data.dto.NotificationWeatherInfoDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("search.json")
    suspend fun searchCities(@Query("q") cityQuery: String): List<SearchCityItemDto>

    @GET("current.json")
    suspend fun getCityWeather(@Query("q") city: String): CityWeatherDto

    @GET("forecast.json")
    suspend fun getForecast(@Query("q") city: String, @Query("days") days: Int, @Query("dt") date: String): ForecastDto

    @GET("forecast.json")
    suspend fun getNotificationWeather(@Query("q") city: String): NotificationWeatherInfoDto
}