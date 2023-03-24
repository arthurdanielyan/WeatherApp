package com.bignerdranch.android.weather.core.domain.usecases

import android.graphics.Bitmap
import com.bignerdranch.android.weather.core.domain.repository.SharedRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetIconUseCase @Inject constructor (
    private val sharedRepository: SharedRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(iconUrl: String): Bitmap =
        withContext(coroutineDispatcher) {
            sharedRepository.getIcon(iconUrl)
        }
}