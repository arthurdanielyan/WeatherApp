package com.bignerdranch.android.weather.feature_5_days_forecast.presentation

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.weather.core.constants.ARG_CITY
import com.bignerdranch.android.weather.core.domain.usecases.GetIconUseCase
import com.bignerdranch.android.weather.core.model.WeatherInfo
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.core.model.WeatherInfoList
import com.bignerdranch.android.weather.feature_5_days_forecast.domain.usecases.GetFiveDayForecastUseCase
import com.bignerdranch.android.weather.feature_5_days_forecast.presentation.state_wrappers.FiveDaysForecastState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            val updatedList = mutableListOf<Bitmap>()
            fiveDaysForecastState.value.list!!.forecastDays.forEach { day ->
                updatedList.add(getIconUseCase(day.iconUrl))
            }
            val daysWithIcons = mutableListOf<WeatherInfo>()
            fiveDaysForecastState.value.list!!.forecastDays.forEachIndexed { index, day ->
                daysWithIcons.add(
                    day.copy(
                        icon = updatedList[index]
                    )
                )
            }
            _fiveDaysForecastState.value = FiveDaysForecastState(
                isLoading = false,
                list = WeatherInfoList(daysWithIcons),
                error = ""
            )
            areIconsLoaded = true
        }
    }
}