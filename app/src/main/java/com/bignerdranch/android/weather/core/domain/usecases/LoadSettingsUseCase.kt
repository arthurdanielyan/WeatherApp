package com.bignerdranch.android.weather.core.domain.usecases

import com.bignerdranch.android.weather.core.domain.repository.LoadSettingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

/** Should be injected into the VM of the launcher screen and the setting screen VM */
class LoadSettingsUseCase @Inject constructor (
    private val loadSettingsRepository: LoadSettingsRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke() {
        withContext(coroutineDispatcher) {
            loadSettingsRepository.loadSettings()
        }
    }
}