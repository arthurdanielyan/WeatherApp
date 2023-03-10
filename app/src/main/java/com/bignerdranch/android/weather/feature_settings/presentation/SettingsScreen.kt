package com.bignerdranch.android.weather.feature_settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bignerdranch.android.weather.core.app_settings.Units
import com.bignerdranch.android.weather.core.presentation.components.TopBar
import com.bignerdranch.android.weather.feature_settings.presentation.components.UnitSelector


@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navController: NavController
) {
    var isDropdownOpen by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .focusable(!isDropdownOpen)
    ) {
        Column(
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
                onChooseUnit = {
                    viewModel.saveUnit(it)
                },
                onDropdownStateChange = { isDropdownOpen = it }
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = if(isDropdownOpen) Color(0x45101010) else Color.Transparent)
        ){}
    }
}