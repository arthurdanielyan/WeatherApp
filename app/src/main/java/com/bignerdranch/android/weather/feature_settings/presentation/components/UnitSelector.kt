package com.bignerdranch.android.weather.feature_settings.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.weather.core.app_settings.Units

@Preview
@Composable
fun UnitSelectorPreview() {
    UnitSelector(
        unit = Units.TempUnits.CELSIUS,
        chosenUnit = Units.selectedTempUnit,
        onChooseUnit = {}
    )
}

@Composable
fun UnitSelector(
    unit: Units, // random enum of Specific unit type
    chosenUnit: Units,
    onChooseUnit: (Units) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = true
                }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(2f),
                text = unit.unitOf,
                style = MaterialTheme.typography.h5,
                color = Color.White
            )
            Text(
                modifier = Modifier.weight(1f),
                text = chosenUnit.unitName,
                style = MaterialTheme.typography.subtitle2,
                color = Color(0xFF818181)
            )
        }
        DropdownMenu(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp)),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {
            unit.options.forEach { unit: Units ->
                DropdownMenuItem(onClick = {
                    onChooseUnit(unit)
                }) {
                    Text(
                        text = unit.unitName,
                        color = if (unit.unitName == chosenUnit.unitName) Color.Blue else Color.White
                    )
                }
            }
        }
    }
}