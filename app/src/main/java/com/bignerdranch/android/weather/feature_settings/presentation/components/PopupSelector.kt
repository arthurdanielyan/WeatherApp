package com.bignerdranch.android.weather.feature_settings.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.bignerdranch.android.weather.core.constants.log


@Composable
fun<T> PopupSelector(
    options: List<T>,
    optionName: (item: T) -> String,
    onSelect: (item: T) -> Unit,
    onDropdownStateChange: (Boolean) -> Unit,
    expanded: Boolean
) {

    log("PopupSelector composition")

    val density = LocalDensity.current
    Popup(
        alignment = Alignment.TopEnd,
        properties = PopupProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
        onDismissRequest = {
            onDropdownStateChange(false)
        },
        offset = IntOffset(
            density.run {
                -(16.toDp().toPx().toInt())
            },
            density.run {
                180.toDp().toPx().toInt()
            }
        )
    ) {
        AnimatedVisibility(
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
            Column(
                modifier = Modifier
                    .background(
                        color = Color(0xFF353535),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .heightIn(0.dp, 300.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                SingleSelectableLazyColumn(
                    unselectedItem = { city ->
                        OptionItem(
                            optionName = optionName(city),
                            selected = false
                        )
                    },
                    selectedItem = { city ->
                        OptionItem(
                            optionName = optionName(city),
                            selected = true
                        )
                    },
                    onSelect = onSelect,
                    selectedItemIndex = 0,
                    items = options
                )
            }
        }
    }
}