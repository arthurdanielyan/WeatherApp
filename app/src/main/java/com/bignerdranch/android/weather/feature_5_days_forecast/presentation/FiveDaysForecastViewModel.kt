package com.bignerdranch.android.weather.feature_5_days_forecast.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.weather.core.ARG_CITY
import com.bignerdranch.android.weather.core.domain.usecases.GetIconUseCase
import com.bignerdranch.android.weather.core.log
import com.bignerdranch.android.weather.core.model.ForecastDay
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.core.model.ShortForecastList
import com.bignerdranch.android.weather.feature_5_days_forecast.domain.usecases.GetFiveDayForecastUseCase
import com.bignerdranch.android.weather.feature_5_days_forecast.presentation.state_wrappers.FiveDaysForecastState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FiveDaysForecastViewModel @Inject constructor(
    private val getFiveDaysForecastUseCase: GetFiveDayForecastUseCase,
    private val getIconUseCase: GetIconUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _fiveDaysForecastState = MutableStateFlow(FiveDaysForecastState())
    val fiveDaysForecastState = _fiveDaysForecastState.asStateFlow()

    private var areIconsLoaded = false

    init {
        savedStateHandle.get<String>(ARG_CITY)?.let {
            getFiveDaysForecast(it)
        }
    }

    private fun getFiveDaysForecast(city: String) {
        viewModelScope.launch {
            getFiveDaysForecastUseCase(city).collect { result ->
                when (result) {
                    is Result.Success -> {
                        _fiveDaysForecastState.value = FiveDaysForecastState(
                            isLoading = false,
                            list = result.data!!,
                            error = ""
                        )
                        loadIcons()
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

    private fun loadIcons() {
        if (areIconsLoaded) return
        viewModelScope.launch {
            var updatedList = mutableListOf<ForecastDay>()
            fiveDaysForecastState.value.list!!.forecastDays.forEachIndexed { index, day ->
                updatedList =
                    fiveDaysForecastState.value.list!!.forecastDays.toMutableList().apply {
                        this[index].icon = getIconUseCase(day.iconUrl)
                    }
            }
            _fiveDaysForecastState.value = _fiveDaysForecastState.value.copy(
                list = ShortForecastList(updatedList)
            )
            areIconsLoaded = true
        }
    }
}