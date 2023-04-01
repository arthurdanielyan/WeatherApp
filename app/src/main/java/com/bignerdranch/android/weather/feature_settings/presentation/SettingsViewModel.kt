package com.bignerdranch.android.weather.feature_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.weather.core.app_settings.Units
import com.bignerdranch.android.weather.feature_settings.domain.model.MyCity
import com.bignerdranch.android.weather.feature_settings.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val saveTempUnitUseCase: SaveTempUnitUseCase,
    private val saveTimeUseCase: SaveTimeUseCase,
    private val saveWeatherAlertOnUseCase: SaveWeatherAlertOnUseCase,
    private val getMyCitiesUseCase: GetMyCitiesUseCase,
    private val saveCityUseCase: SaveCityUseCase
) : ViewModel() {

    private val _myCities = MutableStateFlow(emptyList<MyCity>())
    val myCities = _myCities.asStateFlow()

    init {
        loadCities()
    }

    private fun loadCities() {
        viewModelScope.launch {
            _myCities.value = getMyCitiesUseCase()
        }
    }

    fun saveUnit(unit: Units) {
        viewModelScope.launch {
            when (unit) {
                is Units.TempUnits -> {
                    saveTempUnitUseCase(unit.key)
                }
            }
        }
    }

    fun saveTime(time: String) {
        viewModelScope.launch {
            saveTimeUseCase(time)
        }
    }

    fun saveIsNotificationOn(isOn: Boolean) {
        viewModelScope.launch {
            saveWeatherAlertOnUseCase(isOn)
        }
    }

    fun saveCity(cityId: Float) {
        viewModelScope.launch {
            saveCityUseCase(cityId)
        }
    }
}