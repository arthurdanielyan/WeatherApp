package com.bignerdranch.android.weather.feature_settings.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SettingOptionRow(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    settingTitle: String,
    selectedOption: String,
    showDropDownIcon: Boolean,
    enabled: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                enabled = enabled,
                onClick = onClick
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(2f),
            text = settingTitle,
            style = MaterialTheme.typography.h5,
            color = Color.White
        )
        Text(
            text = selectedOption,
            style = MaterialTheme.typography.subtitle2,
            color = Color(0xFF818181)
        )
        if(showDropDownIcon) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown"
            )
        }
    }
}