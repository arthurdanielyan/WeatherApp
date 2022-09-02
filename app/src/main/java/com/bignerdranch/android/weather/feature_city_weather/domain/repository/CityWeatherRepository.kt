package com.bignerdranch.android.weather.feature_city_weather.domain.repository

import android.graphics.Bitmap
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.feature_city_weather.domain.model.CityWeather
import kotlinx.coroutines.flow.Flow

interface CityWeatherRepository {

    suspend fun getWeather(city: String): Flow<Result<CityWeather>>

    suspend fun getIcon(): Bitmap
}