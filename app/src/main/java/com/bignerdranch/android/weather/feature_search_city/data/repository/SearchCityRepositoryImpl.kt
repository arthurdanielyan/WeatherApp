package com.bignerdranch.android.weather.feature_search_city.data.repository

import com.bignerdranch.android.weather.core.NO_INTERNET_MESSAGE
import com.bignerdranch.android.weather.core.data.api.WeatherApi
import com.bignerdranch.android.weather.core.data.dto.toShortWeatherInfo
import com.bignerdranch.android.weather.core.data.room.daos.MyCitiesDao
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.feature_search_city.data.model.ShortWeatherInfo
import com.bignerdranch.android.weather.feature_search_city.domain.repository.SearchCityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SearchCityRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
    private val myCitiesDao: MyCitiesDao
) : SearchCityRepository {

    @Suppress("SENSELESS_COMPARISON")
    override suspend fun searchCity(cityName: String): Flow<Result<ShortWeatherInfo>> = flow {
        try {
            emit(Result.Loading())
            val currentWeather = weatherApi.getShortWeather(cityName).toShortWeatherInfo()
            if (currentWeather.city != null) // not always non-null
                emit(Result.Success(currentWeather))
        } catch (e: HttpException) {
            emit(Result.Error("Nothing found"))
        } catch (e: IOException) {
            emit(Result.Error(NO_INTERNET_MESSAGE))
        }
    }

    override suspend fun getCities(): Flow<List<ShortWeatherInfo>> = myCitiesDao.getSavedCities()
}