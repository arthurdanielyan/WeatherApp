package com.bignerdranch.android.weather.feature_5_days_forecast.domain.repository

import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.core.model.WeatherInfoList
import kotlinx.coroutines.flow.Flow

interface FiveDaysForecastRepository {

    suspend fun getFiveDayForecast(city: String): Flow<Result<WeatherInfoList>>
}