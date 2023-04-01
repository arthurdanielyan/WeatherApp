package com.bignerdranch.android.weather.feature_settings.domain.usecases

import com.bignerdranch.android.weather.feature_settings.domain.repository.SaveSettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveCityUseCase @Inject constructor(
    private val saveSettingsRepository: SaveSettingsRepository,
    private val coroutineDispatcher: CoroutineDispatcher
){

    suspend operator fun invoke(cityId: Float) =
        withContext(coroutineDispatcher) {
            saveSettingsRepository.saveCity(cityId)
        }
}