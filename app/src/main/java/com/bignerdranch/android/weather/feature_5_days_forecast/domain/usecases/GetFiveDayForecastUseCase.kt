package com.bignerdranch.android.weather.feature_5_days_forecast.domain.usecases

import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.core.model.WeatherInfoList
import com.bignerdranch.android.weather.feature_5_days_forecast.domain.repository.FiveDaysForecastRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFiveDayForecastUseCase @Inject constructor (
    private val repository: FiveDaysForecastRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(city: String): Flow<Result<WeatherInfoList>> =
        withContext(coroutineDispatcher) {
            repository.getFiveDayForecast(city)
        }

}