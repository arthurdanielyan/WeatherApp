package com.bignerdranch.android.weather.feature_city_weather.data.repository

import com.bignerdranch.android.weather.core.API_ERROR_MESSAGE
import com.bignerdranch.android.weather.core.NO_INTERNET_MESSAGE
import com.bignerdranch.android.weather.core.data.api.WeatherApi
import com.bignerdranch.android.weather.core.data.dto.mapper.toCityWeather
import com.bignerdranch.android.weather.core.data.dto.mapper.toShortForecast
import com.bignerdranch.android.weather.core.data.room.daos.MyCitiesDao
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.core.model.ShortForecastList
import com.bignerdranch.android.weather.feature_city_weather.domain.model.CityWeather
import com.bignerdranch.android.weather.feature_city_weather.domain.repository.CityWeatherRepository
import com.bignerdranch.android.weather.feature_search_city.domain.model.ShortWeatherInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CityWeatherRepositoryImpl @Inject constructor (
    private val weatherApi: WeatherApi,
    private val myCitiesDao: MyCitiesDao
) : CityWeatherRepository {

    private lateinit var iconLink: String

//    private val myCitiesDao = appDatabase.myCitiesDao

    override suspend fun getWeather(city: String): Flow<Result<CityWeather>> = flow {
        try {
            emit(Result.Loading())
            val cityWeatherDto = weatherApi.getCityWeather(city)
            iconLink = cityWeatherDto.current.condition.iconUrl
            emit(Result.Success(cityWeatherDto.toCityWeather()))
        } catch (e: HttpException) {
            emit(Result.Error(API_ERROR_MESSAGE))
        } catch (e: IOException) {
            emit(Result.Error(NO_INTERNET_MESSAGE))
        }
    }

    override suspend fun get3DaysForecast(city: String): Flow<Result<ShortForecastList>> = flow {
        try {
            emit(Result.Loading())
            val shortForecast = weatherApi.getForecast(city)
            emit(Result.Success(shortForecast.toShortForecast()))
        } catch (e: HttpException) {
            emit(Result.Error(API_ERROR_MESSAGE))
        } catch (e: IOException) {
            emit(Result.Error(NO_INTERNET_MESSAGE))
        }
    }

    override suspend fun saveCity(city: ShortWeatherInfo) {
        myCitiesDao.insert(city)
    }
}