package com.bignerdranch.android.weather.feature_5_days_forecast.domain.usecases

import com.bignerdranch.android.weather.feature_5_days_forecast.domain.repository.FiveDaysForecastRepository
import kotlinx.coroutines.*

class GetFiveDayForecastUseCase(
    private val repository: FiveDaysForecastRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(city: String) =
        withContext(coroutineDispatcher) {
            repository.getFiveDayForecast(city)
        }

}