package com.bignerdranch.android.weather.feature_settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.bignerdranch.android.weather.core.app_settings.Units
import com.bignerdranch.android.weather.core.presentation.components.TopBar
import com.bignerdranch.android.weather.feature_settings.presentation.components.UnitSelector


@Composable
fun SettingsScreen(
    settingsRepository: SettingsViewModel,
    navController: NavController
) {
    Column (
        modifier = Modifier
            .fillMaxSize(),
    ) {
        TopBar(
            screenTitle = "Unit Configuration",
            navController = navController
        )
        UnitSelector(
            unit = Units.TempUnits.CELSIUS,
            chosenUnit = Units.selectedTempUnit,
            onChooseUnit = {}
        )
    }
}