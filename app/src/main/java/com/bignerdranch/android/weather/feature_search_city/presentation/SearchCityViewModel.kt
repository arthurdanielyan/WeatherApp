package com.bignerdranch.android.weather.feature_search_city.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.weather.core.log
import com.bignerdranch.android.weather.core.model.Result
import com.bignerdranch.android.weather.feature_search_city.domain.model.ShortWeatherInfo
import com.bignerdranch.android.weather.feature_search_city.domain.usecases.GetMyCitiesUseCase
import com.bignerdranch.android.weather.feature_search_city.domain.usecases.SearchCityUseCase
import com.bignerdranch.android.weather.feature_search_city.presentation.state_wrappers.MyCitiesState
import com.bignerdranch.android.weather.feature_search_city.presentation.state_wrappers.ShortWeatherInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val searchCityUseCase: SearchCityUseCase,
    private val getMyCitiesUseCase: GetMyCitiesUseCase
) : ViewModel() {

    private val _searchedCityWeather = MutableStateFlow(ShortWeatherInfoState())
    val searchedCityWeather = _searchedCityWeather.asStateFlow()

    private val _myCities = MutableStateFlow(MyCitiesState())
    val myCities = _myCities.asStateFlow()

    private var searching: Job? = null

    init {
        log("SearchVCityViewModel init")
        getMyCities()
    }

    fun searchCity(cityName: String) {
        searching?.cancel()
        searching = viewModelScope.launch {
            searchCityUseCase(cityName).collect { result: Result<ShortWeatherInfo> ->
                when(result) {
                    is Result.Success -> {
                        _searchedCityWeather.value = ShortWeatherInfoState(
                            isLoading = false,
                            shortWeatherInfo = result.data!!,
                            error = ""
                        )
                    }
                    is Result.Loading -> {
                        _searchedCityWeather.value = ShortWeatherInfoState(
                            isLoading = true,
                            shortWeatherInfo = null,
                            error = ""
                        )
                    }
                    is Result.Error -> {
                        _searchedCityWeather.value = ShortWeatherInfoState(
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
                        _myCities.value = MyCitiesState(
                            isLoading = true,
                            myCities = null,
                            error = ""
                        )
                    }
                    is Result.Success -> {
                        _myCities.value = MyCitiesState(
                            isLoading = false,
                            myCities = result.data!!,
                            error = ""
                        )
                        log("myCitiesLoaded")
                    }
                    is Result.Error -> {
                        _myCities.value = MyCitiesState(
                            isLoading = false,
                            myCities = null,
                            error = result.message!!
                        )
                    }
                }
            }
        }
    }
}