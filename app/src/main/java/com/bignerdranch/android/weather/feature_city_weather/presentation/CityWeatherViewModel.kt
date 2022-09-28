package com.bignerdranch.android.weather.feature_city_weather.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.weather.core.ARG_CITY
import com.bignerdranch.android.weather.core.log
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.Get3DaysForecastUseCase
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.GetCityWeatherUseCase
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.GetIconUseCase
import com.bignerdranch.android.weather.feature_city_weather.presentation.state_wrappers.CityWeatherState
import com.bignerdranch.android.weather.feature_city_weather.presentation.state_wrappers.ShortForecastState
import com.bignerdranch.android.weather.feature_city_weather.presentation.state_wrappers.WeatherIconState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityWeatherViewModel @Inject constructor(
    private val getCityWeatherUseCase: GetCityWeatherUseCase,
    private val get3DaysForecastUseCase: Get3DaysForecastUseCase,
    private val getIconUseCase: GetIconUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _currentWeatherState: MutableState<CityWeatherState> = mutableStateOf(
        CityWeatherState()
    )
    val currentWeatherState: State<CityWeatherState> = _currentWeatherState

    private val _weatherIcon = mutableStateOf(WeatherIconState())
    val weatherIcon: State<WeatherIconState> = _weatherIcon

    private val _shortForecastState = MutableStateFlow(ShortForecastState())
    val shortForecastState = _shortForecastState.asStateFlow()

    init {
        savedStateHandle.get<String>(ARG_CITY)?.let {
            getCityWeather(it)
            get3DayShortWeather(it)
        }
    }

    private fun getCityWeather(city: String) {
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

    private fun get3DayShortWeather(city: String) {
        viewModelScope.launch {
            get3DaysForecastUseCase(city).collect { result ->
                when(result) {
                    is Result.Loading -> {
                        _shortForecastState.value = ShortForecastState(
                            isLoading = true,
                            shortForecast = null,
                            error = ""
                        )
                    }
                    is Result.Success -> {
                        _shortForecastState.value = ShortForecastState(
                            isLoading = false,
                            shortForecast = result.data!!,
                            error = ""
                        )
                    }
                    is Result.Error -> {
                        _shortForecastState.value = ShortForecastState(
                            isLoading = false,
                            shortForecast = null,
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