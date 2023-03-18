package com.bignerdranch.android.weather.feature_settings.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bignerdranch.android.weather.core.app_settings.SettingsStorage
import com.bignerdranch.android.weather.core.app_settings.Units
import com.bignerdranch.android.weather.core.constants.planWeatherAlertNotification
import com.bignerdranch.android.weather.core.constants.turnOffNotification
import com.bignerdranch.android.weather.core.presentation.components.TopBar
import com.bignerdranch.android.weather.feature_settings.presentation.components.UnitSelector
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navController: NavController
) {
    var isDropdownOpen by remember { mutableStateOf(false) }
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
                .fillMaxSize(),
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
            UnitSelector(
                unit = Units.TempUnits.CELSIUS,
                chosenUnit = Units.selectedTempUnit,
                onChooseUnit = {
                    viewModel.saveUnit(it)
                },
                onDropdownStateChange = { isDropdownOpen = it }
            )
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
                var checked by remember { mutableStateOf(SettingsStorage.isWeatherAlertNotificationsEnabled) }
                Switch(
                    checked = checked,
                    onCheckedChange = {
                        viewModel.saveIsNotificationOn(it)
                        checked = it
                        if(!it) {
                            turnOffNotification(context)
                        } else {
                            planWeatherAlertNotification(context, pickedTime)
                        }
                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        timePickerDialogState.show()
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(2f),
                    text = "Alert time",
                    style = MaterialTheme.typography.h5,
                    color = Color.White
                )
                Text(
                    text = DateTimeFormatter.ofPattern("HH:mm").format(pickedTime),
                    style = MaterialTheme.typography.subtitle2,
                    color = Color(0xFF818181)
                )
            }
            // NOTIFICATION SETTINGS end
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
                    planWeatherAlertNotification(context, pickedTime)
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