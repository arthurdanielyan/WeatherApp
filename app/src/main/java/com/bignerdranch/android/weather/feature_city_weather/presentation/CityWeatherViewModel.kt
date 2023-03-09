package com.bignerdranch.android.weather.feature_city_weather.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.weather.core.data.constants.ARG_CITY
import com.bignerdranch.android.weather.core.domain.usecases.GetIconUseCase
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.feature_city_weather.domain.model.HourForecast
import com.bignerdranch.android.weather.feature_city_weather.domain.model.toShortWeatherInfo
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.Get3DaysForecastUseCase
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.GetCityWeatherUseCase
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.GetHourlyForecastUseCase
import com.bignerdranch.android.weather.feature_city_weather.domain.usecases.SaveCityUseCase
import com.bignerdranch.android.weather.feature_city_weather.presentation.state_wrappers.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityWeatherViewModel @Inject constructor(
    private val getCityWeatherUseCase: GetCityWeatherUseCase,
    private val get3DaysForecastUseCase: Get3DaysForecastUseCase,
    private val getIconUseCase: GetIconUseCase,
    private val saveCityUseCase: SaveCityUseCase,
    private val getHourlyForecastUseCase: GetHourlyForecastUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _currentWeatherState = MutableStateFlow(CityWeatherState())
    val currentWeatherState = _currentWeatherState.asStateFlow()

    private val _weatherIcon = MutableStateFlow(WeatherIconState())
    val weatherIcon = _weatherIcon.asStateFlow()

    private val _shortForecastState = MutableStateFlow(ShortForecastState())
    val shortForecastState = _shortForecastState.asStateFlow()

    private val _screenEventsChannel: Channel<ScreenEvent> = Channel()
    val screenEvents = _screenEventsChannel.receiveAsFlow()

    private val _hourlyForecastState = MutableStateFlow(HourlyForecastState())
    val hourlyForecastState = _hourlyForecastState.asStateFlow()

    init {
        savedStateHandle.get<String>(ARG_CITY)?.let {
            getCityWeather(it)
        }
    }

    fun getCityWeather(city: String) {
        _shortForecastState.value = ShortForecastState() // makes progress bar appear for short forecast cards
        _hourlyForecastState.value = HourlyForecastState()
        viewModelScope.launch {
            getCityWeatherUseCase(city).collect { result ->
                when(result) {
                    is Result.Success -> {
                        _currentWeatherState.value = CityWeatherState(
                            isLoading = false,
                            value = result.data!!,
                            error = ""
                        )
                        getIcon(result.data.iconUrl)
                        get3DayShortWeather(city)
                        getHourlyForecast(city)
                    }
                    is Result.Loading -> {
                        _currentWeatherState.value = CityWeatherState(
                            isLoading = true,
                            value = null,
                            error = ""
                        )
                    }
                    is Result.Error -> {
                        _currentWeatherState.value = CityWeatherState(
                            isLoading = false,
                            value = null,
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
                            value = null,
                            error = ""
                        )
                    }
                    is Result.Success -> {
                        _shortForecastState.value = ShortForecastState(
                            isLoading = false,
                            value = result.data!!,
                            error = ""
                        )
                    }
                    is Result.Error -> {
                        _shortForecastState.value = ShortForecastState(
                            isLoading = false,
                            value = null,
                            error = result.message!!
                        )
                    }
                }
            }
        }
    }

    private fun getIcon(iconUrl: String) {
        viewModelScope.launch {
            _weatherIcon.value = WeatherIconState(getIconUseCase(iconUrl))
        }
    }
    
    fun saveCity() {
        viewModelScope.launch {
            if(_currentWeatherState.value.value != null) {
                saveCityUseCase(_currentWeatherState.value.value!!.toShortWeatherInfo())
                _screenEventsChannel.send(ScreenEvent.ShowToast("City Saved"))
            } else {
                _screenEventsChannel.send(ScreenEvent.ShowToast("Couldn't save city"))
            }
        }
    }

    private fun getHourlyForecast(city: String) {
        viewModelScope.launch {
            getHourlyForecastUseCase(city).collect { result ->
                when(result) {
                    is Result.Loading -> {
                        _hourlyForecastState.value = HourlyForecastState(
                            isLoading = true,
                            list = null,
                            error = ""
                        )
                    }
                    is Result.Success -> {
                        _hourlyForecastState.value = HourlyForecastState(
                            isLoading = true,
                            list = result.data!!,
                            error = ""
                        )
                        loadHourlyForecastIcons()
                    }
                    is Result.Error -> {
                        _hourlyForecastState.value = HourlyForecastState(
                            isLoading = false,
                            list = null,
                            error = result.message!!
                        )
                    }
                }
            }
        }
    }

    private fun loadHourlyForecastIcons() {
        viewModelScope.launch {
            val withoutIconsList: List<HourForecast> = hourlyForecastState.value.list!!
            val withIconsList = mutableListOf<HourForecast>()
            withoutIconsList.forEachIndexed { index, hourForecast ->
                withIconsList.add(withoutIconsList[index].copy(icon = getIconUseCase(hourForecast.iconUrl)))
            }
            _hourlyForecastState.value = HourlyForecastState(
                isLoading = false,
                list = withIconsList,
                error = ""
            )
        }
    }
}