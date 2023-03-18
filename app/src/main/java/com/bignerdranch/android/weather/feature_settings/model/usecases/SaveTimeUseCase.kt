package com.bignerdranch.android.weather.feature_settings.model.usecases

import com.bignerdranch.android.weather.feature_settings.model.repository.SaveSettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveTimeUseCase @Inject constructor(
    private val settingsRepository: SaveSettingsRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(time: String) =
        withContext(coroutineDispatcher) {
            settingsRepository.saveTime(time)
        }
}