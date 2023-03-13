package com.bignerdranch.android.weather.core.presentation.components

import android.view.MotionEvent.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
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
    var size by remember { mutableStateOf(IntSize(0,0)) }
    hoverColor = Color(
        red = rangeAsRgb(hoverColor.red.toInt()*255-50),
        green = rangeAsRgb(hoverColor.green.toInt()*255-50),
        blue = rangeAsRgb(hoverColor.blue.toInt()*255-50),
    )
    Icon(
        modifier = modifier
            .onGloballyPositioned {
                size = it.size
            }
            .pointerInteropFilter { event ->
                when(event.actionMasked) {
                    ACTION_DOWN -> {
                        isIconHovered = true
                    }
                    ACTION_CANCEL -> {
                        isIconHovered = false
                    }
                    ACTION_UP -> {
                        if(isIconHovered) {
                            onClick()
                            isIconHovered = false
                        }
                    }
                    ACTION_MOVE -> {
                        if(!(event.x.toInt() in 0..size.width && event.y.toInt() in 0..size.height))
                            isIconHovered = false
                    }
                }
                true
            },
        imageVector = imageVector,
        contentDescription = contentDescription,
        tint = if(isIconHovered) hoverColor else MaterialTheme.colors.defaultTint
    )
}

fun rangeAsRgb(x: Int): Int = x.toUByte().toInt()