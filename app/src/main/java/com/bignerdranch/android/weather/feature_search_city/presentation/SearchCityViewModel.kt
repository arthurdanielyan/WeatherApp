package com.bignerdranch.android.weather.feature_search_city.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.weather.core.log
import com.bignerdranch.android.weather.feature_search_city.domain.model.ShortWeatherInfo
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.feature_search_city.domain.usecases.GetMyCitiesUseCase
import com.bignerdranch.android.weather.feature_search_city.domain.usecases.SearchCityUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val searchCityUseCase: SearchCityUseCase,
    private val getMyCitiesUseCase: GetMyCitiesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ShortWeatherInfoState())
    val state: State<ShortWeatherInfoState> = _state

    private var searching: Job? = null

    init {
        getMyCities()
    }

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

    private fun getMyCities() {
        viewModelScope.launch {
            getMyCitiesUseCase().collect { result ->
                when(result) {
                    is Result.Loading -> {
                        log("my cities loading")
                    }
                    is Result.Success -> {
                        log("my cities success ${result.data!!}")
                    }
                    is Result.Error -> {
                        log("error ${result.message}")
                    }
                }
            }
        }
    }
}