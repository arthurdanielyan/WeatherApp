package com.bignerdranch.android.weather.feature_city_weather.data.repository

import com.bignerdranch.android.weather.core.constants.API_ERROR_MESSAGE
import com.bignerdranch.android.weather.core.constants.NO_INTERNET_MESSAGE
import com.bignerdranch.android.weather.core.data.api.WeatherApi
import com.bignerdranch.android.weather.core.data.dto.mapper.toCityWeather
import com.bignerdranch.android.weather.core.data.dto.mapper.toHourForecast
import com.bignerdranch.android.weather.core.data.dto.mapper.toShortForecast
import com.bignerdranch.android.weather.core.data.room.daos.MyCitiesDao
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.core.model.WeatherInfoList
import com.bignerdranch.android.weather.feature_city_weather.domain.model.CityWeather
import com.bignerdranch.android.weather.feature_city_weather.domain.model.HourForecast
import com.bignerdranch.android.weather.feature_city_weather.domain.repository.CityWeatherRepository
import com.bignerdranch.android.weather.feature_search_city.data.model.ShortWeatherInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import java.util.Calendar.*
import javax.inject.Inject

class CityWeatherRepositoryImpl @Inject constructor (
    private val weatherApi: WeatherApi,
    private val myCitiesDao: MyCitiesDao
) : CityWeatherRepository {


    override suspend fun getWeather(city: String): Flow<Result<CityWeather>> = flow {
        try {
            emit(Result.Loading())
            val cityWeatherDto = weatherApi.getCityWeather(city)
            emit(Result.Success(cityWeatherDto.toCityWeather()))
        } catch (e: HttpException) {
            emit(Result.Error(API_ERROR_MESSAGE))
        } catch (e: IOException) {
            emit(Result.Error(NO_INTERNET_MESSAGE))
        }
    }

    override suspend fun get3DaysForecast(city: String): Flow<Result<WeatherInfoList>> = flow {
        try {
            emit(Result.Loading())
            val shortForecast = weatherApi.getForecast(city,3, "")
            // empty string is because of bad api
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

    override suspend fun getHourlyForecast(city: String): Flow<Result<List<HourForecast>>> = flow {
        try {
            val calendar = GregorianCalendar()
            val todayString = "${calendar.get(YEAR)}-${calendar.get(MONTH)+1}-${calendar.get(DAY_OF_MONTH)}"
            val todayForecast = weatherApi.getForecast(city, 1, todayString)

            val tomorrowString = "${calendar.get(YEAR)}-${calendar.get(MONTH)+1}-${calendar.get(DAY_OF_MONTH)+1}"
            val tomorrowForecast = weatherApi.getForecast(city, 1, tomorrowString)

            val nextHour = calendar.get(HOUR_OF_DAY)+1
            val finalHourlyForecastList = todayForecast.forecastDays.forecastDays[0].hours
                .subList(nextHour, 24).toMutableList()
            finalHourlyForecastList.addAll(
                tomorrowForecast.forecastDays.forecastDays[0].hours
                    .subList(0, 24-finalHourlyForecastList.size)
            )
            emit(Result.Success(finalHourlyForecastList.toList().map { it.toHourForecast() }))
        } catch (e: HttpException) {
            emit(Result.Error(API_ERROR_MESSAGE))
        } catch (e: IOException) {
            emit(Result.Error(NO_INTERNET_MESSAGE))
        }
    }

}