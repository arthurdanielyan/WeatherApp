package com.bignerdranch.android.weather.feature_settings.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
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


    Popup(
//        alignment = Alignment.BottomEnd,
        properties = PopupProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
        onDismissRequest = {
            onDropdownStateChange(false)
        },
        popupPositionProvider = BottomEndPopupPositionProvider(),
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
            Column( //options goes here
                modifier = Modifier
                    .background(
                        color = Color(0xFF353535),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .height(300.dp)
                    .widthIn(50.dp, 200.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                SingleSelectableLazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
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
                    items = options,
                )
            }
        }
    }
}

class BottomEndPopupPositionProvider : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        val x = anchorBounds.right - popupContentSize.width
        val y = anchorBounds.bottom
        return IntOffset(x, y)
    }
}