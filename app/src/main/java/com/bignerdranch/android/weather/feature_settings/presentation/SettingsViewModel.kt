package com.bignerdranch.android.weather.feature_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.weather.core.app_settings.Units
import com.bignerdranch.android.weather.feature_settings.model.usecases.SaveTempUnitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val saveTempUnitUseCase: SaveTempUnitUseCase
) : ViewModel() {

    fun saveUnit(unit: Units) {
        viewModelScope.launch {
            when (unit) {
                is Units.TempUnits -> {
                    saveTempUnitUseCase(unit.key)
                }
            }
        }
    }
}