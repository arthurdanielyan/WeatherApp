package com.bignerdranch.android.weather.feature_search_city.domain.usecases

import com.bignerdranch.android.weather.core.log
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.feature_search_city.domain.repository.SearchCityRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class GetMyCitiesUseCase(
    private val repository: SearchCityRepository,
    private val coroutineDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke() =
        withContext(coroutineDispatcher) {
            repository.getCities().map { myCitiesList ->
                val myCitiesUpdated = myCitiesList.toMutableList()
                myCitiesList.forEachIndexed { index, city ->
                    repository.searchCity(city.city).collectLatest { newValuesResult ->
                        when(newValuesResult) {
                            is Result.Success -> {
                                myCitiesUpdated[index] = newValuesResult.data!!
                            }
                            else -> {}
                        }
                    }
                }
                myCitiesUpdated.sortedBy { it.city }
            }
        }
}