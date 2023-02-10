package com.bignerdranch.android.weather.feature_5_days_forecast.data.repository

import com.bignerdranch.android.weather.core.API_ERROR_MESSAGE
import com.bignerdranch.android.weather.core.NO_INTERNET_MESSAGE
import com.bignerdranch.android.weather.core.data.api.WeatherApi
import com.bignerdranch.android.weather.core.data.dto.mapper.toShortForecast
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.core.model.ShortForecastList
import com.bignerdranch.android.weather.feature_5_days_forecast.domain.repository.FiveDaysForecastRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class FiveDaysForecastRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : FiveDaysForecastRepository {

    override suspend fun getFiveDayForecast(city: String): Flow<Result<ShortForecastList>> = flow {
        try {
            emit(Result.Loading())
            val fiveDayForecast = weatherApi.getForecast(city, 3).toShortForecast()
            emit(Result.Success(fiveDayForecast))
        } catch (e: HttpException) {
            emit(Result.Error(API_ERROR_MESSAGE))
        } catch (e: IOException) {
            emit(Result.Error(NO_INTERNET_MESSAGE))
        }
    }
}