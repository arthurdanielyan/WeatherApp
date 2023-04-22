package com.bignerdranch.android.weather.feature_search_city.data.repository

import com.bignerdranch.android.weather.core.constants.NO_INTERNET_MESSAGE
import com.bignerdranch.android.weather.core.data.api.WeatherApi
import com.bignerdranch.android.weather.core.data.dto.SearchCityItemDto
import com.bignerdranch.android.weather.core.data.dto.toShortWeatherInfo
import com.bignerdranch.android.weather.core.data.model.ShortWeatherInfo
import com.bignerdranch.android.weather.core.data.room.daos.MyCitiesDao
import com.bignerdranch.android.weather.core.model.Result
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
    override suspend fun searchCity(cityQuery: String): Flow<Result<List<ShortWeatherInfo>>> = flow {
        try {
            emit(Result.Loading())
            val searchedCities: List<SearchCityItemDto> = weatherApi.searchCities(cityQuery)
            val searchedCitiesWeather = mutableListOf<ShortWeatherInfo>()
            searchedCities.forEach {
                searchedCitiesWeather.add(weatherApi.getCityWeather(it.cityName).toShortWeatherInfo())
            }
            emit(Result.Success(searchedCitiesWeather))
        } catch (e: HttpException) {
            emit(Result.Error("Nothing found"))
        } catch (e: IOException) {
            emit(Result.Error(NO_INTERNET_MESSAGE))
        }
    }

    override suspend fun getCityWeather(city: String): Flow<Result<ShortWeatherInfo>> = flow {
        try {
            emit(Result.Loading())
            val weatherInfo = weatherApi.getCityWeather(city).toShortWeatherInfo()
            emit(Result.Success(weatherInfo))
        } catch (e: HttpException) {
            emit(Result.Error("Nothing found"))
        } catch (e: IOException) {
            emit(Result.Error(NO_INTERNET_MESSAGE))
        }
    }

    override suspend fun getCities(): Flow<List<ShortWeatherInfo>> = myCitiesDao.getSavedCities()
}