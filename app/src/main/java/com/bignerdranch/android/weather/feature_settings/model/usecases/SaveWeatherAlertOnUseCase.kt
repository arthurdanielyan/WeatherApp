package com.bignerdranch.android.weather.feature_settings.model.usecases

import com.bignerdranch.android.weather.feature_settings.model.repository.SaveSettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveWeatherAlertOnUseCase @Inject constructor(
    private val saveSettingsRepository: SaveSettingsRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(isOn: Boolean) =
        withContext(coroutineDispatcher) {
            saveSettingsRepository.saveWeatherAlertOn(isOn)
        }
}