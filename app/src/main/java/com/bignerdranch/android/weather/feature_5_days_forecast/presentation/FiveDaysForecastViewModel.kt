package com.bignerdranch.android.weather.feature_5_days_forecast.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.weather.core.ARG_CITY
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.feature_5_days_forecast.domain.usecases.GetFiveDayForecastUseCase
import com.bignerdranch.android.weather.feature_5_days_forecast.presentation.state_wrappers.FiveDaysForecastState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FiveDaysForecastViewModel @Inject constructor(
    private val getFiveDaysForecastUseCase: GetFiveDayForecastUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _fiveDaysForecastState = MutableStateFlow(FiveDaysForecastState())
    val fiveDaysForecastState = _fiveDaysForecastState.asStateFlow()

    init {
        savedStateHandle.get<String>(ARG_CITY)?.let {
            getFiveDaysForecast(it)
        }
    }

    private fun getFiveDaysForecast(city: String) {
        viewModelScope.launch {
            getFiveDaysForecastUseCase(city).collect { result ->
                when(result) {
                    is Result.Success -> {
                        _fiveDaysForecastState.value = FiveDaysForecastState(
                            isLoading = false,
                            list = result.data!!,
                            error = ""
                        )
                    }
                    is Result.Loading -> {
                        _fiveDaysForecastState.value = FiveDaysForecastState(
                            isLoading = true,
                            list = null,
                            error = ""
                        )
                    }
                    is Result.Error -> {
                        _fiveDaysForecastState.value = FiveDaysForecastState(
                            isLoading = false,
                            list = null,
                            error = result.message!!
                        )
                    }
                }
            }
        }
    }
}