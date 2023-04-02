package com.bignerdranch.android.weather.feature_settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bignerdranch.android.weather.WeatherApplication
import com.bignerdranch.android.weather.core.app_settings.SettingsStorage
import com.bignerdranch.android.weather.core.app_settings.Units
import com.bignerdranch.android.weather.core.presentation.components.TopBar
import com.bignerdranch.android.weather.feature_settings.presentation.components.PopupSelector
import com.bignerdranch.android.weather.feature_settings.presentation.components.SettingOptionRow
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navController: NavController
) {
    var isCitySelectorExpanded by remember { mutableStateOf(false) }
    var isTempSelectorExpanded by remember { mutableStateOf(false) }
    var notificationToggleSwitch by remember { mutableStateOf(SettingsStorage.isWeatherAlertNotificationsEnabled) }
    var isDropdownOpen by remember(isCitySelectorExpanded, isTempSelectorExpanded) {
        mutableStateOf(isCitySelectorExpanded || isTempSelectorExpanded)
    }
    val timePickerDialogState = rememberMaterialDialogState()
    val context = LocalContext.current
    var pickedTime by remember {
        mutableStateOf(LocalTime.of(
            SettingsStorage.notificationTime.substringBefore(':').toInt(),
            SettingsStorage.notificationTime.substringAfter(':').toInt()
        ))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .focusable(!isDropdownOpen)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            TopBar(
                screenTitle = "Settings",
                navController = navController
            )
            Text(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp,
                        bottom = 16.dp,
                    ),
                text = "Unit configurations",
            )
            Divider(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            Box {
                SettingOptionRow(
                    onClick = { isTempSelectorExpanded = true },
                    settingTitle = "Temperature units",
                    selectedOption =
                    Units.TempUnits.CELSIUS.options.find {
                        it.key == Units.selectedTempUnitInt
                    }!!.unitSign,
                    showDropDownIcon = true,
                    enabled = !isDropdownOpen
                )
                PopupSelector(
                    options = Units.TempUnits.CELSIUS.options,
                    selectedItemIndex =
                    Units.TempUnits.CELSIUS.options.indexOfFirst {
                        it.key == Units.selectedTempUnitInt
                    },
                    optionName = {
                        it.unitSign
                    },
                    onSelect = {
                        isTempSelectorExpanded = false
                        viewModel.saveUnit(it)
                    },
                    onDropdownStateChange = {
                        isTempSelectorExpanded = it
                    },
                    expanded = isTempSelectorExpanded
                )
            }
            // NOTIFICATION SETTINGS start
            Text(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp,
                        bottom = 16.dp,
                    ),
                text = "Notification settings",
            )
            Divider(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Daily weather alerts"
                )
                Switch(
                    checked = notificationToggleSwitch,
                    onCheckedChange = {
                        viewModel.saveIsNotificationOn(it)
                        notificationToggleSwitch = it
                        if(!it) {
                            (context.applicationContext as WeatherApplication)
                                .turnOffNotification(context)
                        } else {
                            (context.applicationContext as WeatherApplication)
                                .planWeatherAlertNotification(context, pickedTime)
                        }
                    }
                )
            }
            SettingOptionRow(
                onClick = timePickerDialogState::show,
                settingTitle = "Alert time",
                selectedOption = DateTimeFormatter.ofPattern("HH:mm").format(pickedTime),
                showDropDownIcon = false,
                enabled = !isDropdownOpen
            )
            val cities by viewModel.myCities.collectAsState()
            Box { // SELECT HOME CITY
                SettingOptionRow(
                    onClick = {
                        isCitySelectorExpanded = true
                    },
                    settingTitle = "Select home city",
                    selectedOption = cities.find { it.id == SettingsStorage.homeCityId }?.cityName ?:
                        try {
                            cities[0].cityName
                        } catch (e: IndexOutOfBoundsException) { "Loading..." },
                    showDropDownIcon = true,
                    enabled = !isDropdownOpen
                )
                val selectedCityIndex = cities.indexOfFirst { it.id == SettingsStorage.homeCityId }
                PopupSelector( // HOME CITY SELECTOR
                    options = cities,
                    selectedItemIndex =
                        if(selectedCityIndex == -1) 0 else selectedCityIndex,
                    optionName = {
                        it.cityName
                    },
                    onSelect = {
                        isCitySelectorExpanded = false
                        viewModel.saveCity(it.id)
                    },
                    onDropdownStateChange = {
                        isDropdownOpen = it
                        isCitySelectorExpanded = it
                    },
                    expanded = isCitySelectorExpanded
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = if (isDropdownOpen) Color(0x45101010) else Color.Transparent)
        )
    }
    MaterialDialog(
        dialogState = timePickerDialogState,
        buttons = {
            positiveButton(
                text = "Save",
                onClick = {
                    viewModel.saveTime("${pickedTime.hour}:${pickedTime.minute}")
                    if(notificationToggleSwitch)
                        (context.applicationContext as WeatherApplication)
                            .planWeatherAlertNotification(context, pickedTime)
                }
            )
            negativeButton(text = "Cancel")
        }
    ) {
        this.timepicker(
            initialTime = LocalTime.of(
                SettingsStorage.notificationTime.substringBefore(':').toInt(),
                SettingsStorage.notificationTime.substringAfter(':').toInt()
            ),
            title = "Pick time for daily alerts",
            is24HourClock = true,
            onTimeChange = {
                pickedTime = it
            }
        )
    }
}