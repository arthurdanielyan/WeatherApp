package com.bignerdranch.android.weather.feature_settings.domain.usecases

import com.bignerdranch.android.weather.core.model.MyCity
import com.bignerdranch.android.weather.feature_settings.domain.repository.GetMyCitiesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMyCitiesUseCase @Inject constructor(
    private val getMyCitiesRepository: GetMyCitiesRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(): List<MyCity> =
        withContext(coroutineDispatcher) {
            getMyCitiesRepository.getMyCities()
        }
}