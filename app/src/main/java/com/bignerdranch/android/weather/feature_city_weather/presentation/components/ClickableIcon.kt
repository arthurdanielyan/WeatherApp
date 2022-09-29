package com.bignerdranch.android.weather.feature_city_weather.presentation.components

import android.view.MotionEvent.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import com.bignerdranch.android.weather.ui.theme.defaultTint

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ClickableIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    var isIconHovered by remember { mutableStateOf(false) }
    var hoverColor = MaterialTheme.colors.defaultTint
    hoverColor = Color(
        red = rangeAsRgb(hoverColor.red.toInt()*255-50),
        green = rangeAsRgb(hoverColor.green.toInt()*255-50),
        blue = rangeAsRgb(hoverColor.blue.toInt()*255-50),
    )
    Icon(
        modifier = modifier
            .pointerInteropFilter { event ->
                when(event.actionMasked) {
                    ACTION_DOWN -> {
                        isIconHovered = true
                    }
                    ACTION_CANCEL -> {
                        isIconHovered = false
                    }
                    ACTION_UP -> {
                        isIconHovered = false
                        onClick()
                    }
                }
                true
            }
//            .clickable(
//                indication = null,
//                interactionSource = MutableInteractionSource(),
//                onClick = onClick
//            )
        ,
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = if(isIconHovered) hoverColor else MaterialTheme.colors.defaultTint
    )
}

fun rangeAsRgb(x: Int): Int = x.toUByte().toInt()