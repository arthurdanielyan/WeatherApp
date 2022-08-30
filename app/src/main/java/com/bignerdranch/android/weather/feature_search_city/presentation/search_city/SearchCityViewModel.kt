package com.bignerdranch.android.weather.feature_search_city.presentation.search_city

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.weather.feature_search_city.domain.model.ShortWeatherInfo
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.feature_search_city.domain.usecases.SearchCityUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchCityViewModel(
    private val searchCityUseCase: SearchCityUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ShortWeatherInfoState())
    val state: State<ShortWeatherInfoState> = _state

    private var searching: Job? = null

    fun searchCity(cityName: String) {
        searching?.cancel()
        searching = viewModelScope.launch {
            searchCityUseCase(cityName).collect { result: Result<ShortWeatherInfo> ->
                when(result) {
                    is Result.Success -> {
                        _state.value = ShortWeatherInfoState(
                            isLoading = false,
                            shortWeatherInfo = result.data!!,
                            error = ""
                        )
                    }
                    is Result.Loading -> {
                        _state.value = ShortWeatherInfoState(
                            isLoading = true,
                            shortWeatherInfo = null,
                            error = ""
                        )
                    }
                    is Result.Error -> {
//                        error.setValue(result.message!!)
                        _state.value = ShortWeatherInfoState(
                            isLoading = false,
                            shortWeatherInfo = null,
                            error = result.message!!
                        )
                    }
                }
            }
        }
    }
}