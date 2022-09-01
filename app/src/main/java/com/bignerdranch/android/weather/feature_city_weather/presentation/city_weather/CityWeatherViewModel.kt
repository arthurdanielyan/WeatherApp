package com.bignerdranch.android.weather.feature_city_weather.presentation.city_weather

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.GetCityWeatherUseCase
import kotlinx.coroutines.launch

class CityWeatherViewModel(
    private val getCityWeatherUseCase: GetCityWeatherUseCase
) : ViewModel() {

    private val _currentWeatherState: MutableState<CityWeatherState> = mutableStateOf(CityWeatherState())
    val currentWeatherState: State<CityWeatherState> = _currentWeatherState

    fun getCityWeather(city: String) {
        viewModelScope.launch {
            getCityWeatherUseCase(city).collect { result ->
                when(result) {
                    is Result.Success -> {
                        _currentWeatherState.value = CityWeatherState(
                            isLoading = false,
                            cityWeather = result.data!!,
                            error = ""
                        )
                    }
                    is Result.Loading -> {
                        _currentWeatherState.value = CityWeatherState(
                            isLoading = true,
                            cityWeather = null,
                            error = ""
                        )
                    }
                    is Result.Error -> {
                        _currentWeatherState.value = CityWeatherState(
                            isLoading = false,
                            cityWeather = null,
                            error = result.message!!
                        )
                    }
                }
            }
        }
    }
}