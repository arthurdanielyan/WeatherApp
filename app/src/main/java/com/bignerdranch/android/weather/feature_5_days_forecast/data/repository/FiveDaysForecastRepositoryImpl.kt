package com.bignerdranch.android.weather.feature_5_days_forecast.data.repository

import com.bignerdranch.android.weather.core.constants.API_ERROR_MESSAGE
import com.bignerdranch.android.weather.core.constants.NO_INTERNET_MESSAGE
import com.bignerdranch.android.weather.core.data.api.WeatherApi
import com.bignerdranch.android.weather.core.data.dto.ForecastDto
import com.bignerdranch.android.weather.core.data.dto.mapper.toForecastDay
import com.bignerdranch.android.weather.core.model.WeatherInfo
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.core.model.WeatherInfoList
import com.bignerdranch.android.weather.feature_5_days_forecast.domain.repository.FiveDaysForecastRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import java.util.Calendar.*
import javax.inject.Inject

class FiveDaysForecastRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : FiveDaysForecastRepository {

    override suspend fun getFiveDayForecast(city: String): Flow<Result<WeatherInfoList>> = flow {
        try {
            emit(Result.Loading())
            val forecastDaysDto = mutableListOf<ForecastDto>()
            (0..4).forEach {
                val calendar = GregorianCalendar()
                calendar.add(DAY_OF_MONTH, it)
                val dayString = "${calendar.get(YEAR)}-${calendar.get(MONTH)+1}-${calendar.get(DAY_OF_MONTH)}"
//                log(dayString + " $it")
                val dayForecast = weatherApi.getForecast(city, 1, dayString)
                forecastDaysDto.add(dayForecast)
            }
            val forecastDays = mutableListOf<WeatherInfo>()
            forecastDaysDto.forEach { shortForecastDto ->
                forecastDays.add(shortForecastDto.forecastDays.forecastDays[0].toForecastDay())
            }
            val fiveDayForecast = WeatherInfoList(forecastDays)
            emit(Result.Success(fiveDayForecast))
        } catch (e: HttpException) {
            emit(Result.Error(API_ERROR_MESSAGE))
        } catch (e: IOException) {
            emit(Result.Error(NO_INTERNET_MESSAGE))
        }
    }
}