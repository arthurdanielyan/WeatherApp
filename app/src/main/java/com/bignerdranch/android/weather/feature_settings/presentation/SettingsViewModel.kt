package com.bignerdranch.android.weather.feature_settings.presentation

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.weather.core.domain.repository.LoadSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val loadSettingsRepository: LoadSettingsRepository
) : ViewModel() {

}