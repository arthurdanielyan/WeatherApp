package com.bignerdranch.android.weather.feature_city_weather.data.repository

import com.bignerdranch.android.weather.core.NO_INTERNET_MESSAGE
import com.bignerdranch.android.weather.core.data.WeatherApi
import com.bignerdranch.android.weather.core.data.dto.toCityWeather
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.feature_city_weather.domain.model.CityWeather
import com.bignerdranch.android.weather.feature_city_weather.domain.repository.CityWeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class CityWeatherRepositoryImpl(
    private val weatherApi: WeatherApi
) : CityWeatherRepository {

    override suspend fun getWeather(city: String): Flow<Result<CityWeather>> = flow {
        try {
            emit(Result.Loading())
            val cityWeather = weatherApi.getCityWeather(city)
            emit(Result.Success(cityWeather.toCityWeather()))
        } catch (e: HttpException) {
            emit(Result.Error("An unexpected error occurred."))
        } catch (e: IOException) {
            emit(Result.Error(NO_INTERNET_MESSAGE))
        }
    }
}