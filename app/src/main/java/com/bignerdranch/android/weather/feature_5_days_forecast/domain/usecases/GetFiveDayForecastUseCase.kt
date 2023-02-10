package com.bignerdranch.android.weather.feature_5_days_forecast.domain.usecases

import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.core.model.ShortForecastList
import com.bignerdranch.android.weather.feature_5_days_forecast.domain.repository.FiveDaysForecastRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow

class GetFiveDayForecastUseCase(
    private val repository: FiveDaysForecastRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(city: String): Flow<Result<ShortForecastList>> =
        withContext(coroutineDispatcher) {
            repository.getFiveDayForecast(city)
        }

}