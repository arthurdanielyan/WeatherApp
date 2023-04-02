package com.bignerdranch.android.weather.feature_settings.presentation.components

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.bignerdranch.android.weather.core.app_settings.Units

//@Preview
//@Composable
//fun UnitSelectorPreview() {
//    UnitSelector(
//        unit = Units.TempUnits.CELSIUS,
//        chosenUnit = Units.selectedTempUnit,
//        onChooseUnit = {}
//    )
//}

@Composable
fun UnitSelector(
    unit: Units, // random enum of Specific unit type
    chosenUnit: Units,
    onChooseUnit: (Units) -> Unit,
    onDropdownStateChange: (Boolean) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    LaunchedEffect(expanded) {
        onDropdownStateChange(expanded)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = !expanded) {
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
            text = chosenUnit.unitSign,
            style = MaterialTheme.typography.subtitle2,
            color = Color(0xFF818181)
        )
        Box {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Unit of temperature choice dropdown"
            )

            Popup(
                alignment = Alignment.TopEnd,
                properties = PopupProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                ),
                onDismissRequest = {
                    expanded = false
                },
                offset = IntOffset(0, 150)
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = expanded,
                    enter = fadeIn() + slideInVertically(
                        initialOffsetY = { -it },
                        animationSpec = tween(durationMillis = 500)
                    ),
                    exit = fadeOut() + slideOutVertically(
                        targetOffsetY = { -it },
                        animationSpec = tween(durationMillis = 500)
                    )
                ) {
                    Column( //options goes here
                        modifier = Modifier
                            .width(150.dp)
                            .background(
                                color = Color(0xFF353535),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clip(RoundedCornerShape(12.dp))
                    ) {
//                        SingleSelectableLazyColumn(
//                            modifier = Modifier
//                                .fillMaxWidth(),
//                            unselectedItem = { item ->
//                                OptionItem(
//                                    optionName = item.unitName,
//                                    selected = false
//                                )
//                            },
//                            selectedItem = { item ->
//                                OptionItem(
//                                    optionName = item.unitName,
//                                    selected = true
//                                )
//                            },
//                            onSelect = { item ->
//                                onChooseUnit(item)
//                                expanded = false
//                            },
//                            selectedItemIndex = unit.options.indexOfFirst { it.unitName == chosenUnit.unitName },
//                            items = unit.options,
//                        )
                    }
                }
            }
        }
    }
}