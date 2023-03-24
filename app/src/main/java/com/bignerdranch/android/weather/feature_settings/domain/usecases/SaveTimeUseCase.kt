package com.bignerdranch.android.weather.feature_settings.domain.usecases

import com.bignerdranch.android.weather.feature_settings.domain.repository.SaveSettingsRepository
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