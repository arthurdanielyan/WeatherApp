package com.bignerdranch.android.weather.core.domain.usecases

import android.graphics.Bitmap
import com.bignerdranch.android.weather.core.domain.repository.GetIconRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetIconUseCase @Inject constructor (
    private val getIconRepository: GetIconRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(iconUrl: String): Bitmap =
        withContext(coroutineDispatcher) {
            getIconRepository.getIcon(iconUrl)
        }
}