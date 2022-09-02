package com.bignerdranch.android.weather.feature_city_weather.presentation.city_weather

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.weather.core.log
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.GetCityWeatherUseCase
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.GetIconUseCase
import com.bignerdranch.android.weather.feature_city_weather.presentation.city_weather.state_wrappers.CityWeatherState
import com.bignerdranch.android.weather.feature_city_weather.presentation.city_weather.state_wrappers.WeatherIconState
import kotlinx.coroutines.launch

class CityWeatherViewModel(
    private val getCityWeatherUseCase: GetCityWeatherUseCase,
    private val getIconUseCase: GetIconUseCase
) : ViewModel() {

    private val _currentWeatherState: MutableState<CityWeatherState> = mutableStateOf(
        CityWeatherState()
    )
    val currentWeatherState: State<CityWeatherState> = _currentWeatherState

    private val _weatherIcon = mutableStateOf(WeatherIconState())
    val weatherIcon: State<WeatherIconState> = _weatherIcon

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
                        getIcon()
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

    private fun getIcon() {
        viewModelScope.launch {
            log("getting icon")
            _weatherIcon.value = WeatherIconState(getIconUseCase())
        }
    }
}