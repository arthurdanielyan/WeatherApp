package com.bignerdranch.android.weather.feature_city_weather.presentation.components

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weather.ui.theme.defaultGradientEnd
import com.bignerdranch.android.weather.ui.theme.defaultGradientStart

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ExtremePointsWeatherCard(
    modifier: Modifier = Modifier,
    minTemp: Double,
    maxTemp: Double,
    description: String,
    day: String
) {
    val textSize = 16.sp

    val touchScale = 0.9f
    var targetScale by remember { mutableStateOf(1f) }
    val touchAnimation by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = tween(
            durationMillis = 100
        )
    )
    var isSwipedBackToCard by remember { mutableStateOf(false) }
    var cardWidth by remember { mutableStateOf(0f) }
    var cardHeight by remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(touchAnimation)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(MaterialTheme.colors.defaultGradientEnd.toArgb()),
                            Color(MaterialTheme.colors.defaultGradientStart.toArgb())
                        ),
                        start = Offset.Zero,
                        end = Offset.Infinite
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(19.dp)
                .onGloballyPositioned {
                    val windowBounds = it.boundsInParent()
                    cardWidth = windowBounds.size.width
                    cardHeight = windowBounds.size.height
                }
                .pointerInteropFilter { touch: MotionEvent ->
                    if (touch.x in 0f..cardWidth && touch.y in 0f..cardHeight && !isSwipedBackToCard) {
                        targetScale = touchScale
                        isSwipedBackToCard = false
                    } else {
                        targetScale = 1f
                        isSwipedBackToCard = true
                    }

                    if (touch.actionMasked == MotionEvent.ACTION_CANCEL || touch.actionMasked == MotionEvent.ACTION_UP) {
                        targetScale = 1f
//                        if (touch.x in 0f..cardWidth && touch.y in 0f..cardHeight && !isSwipedBackToCard) {
//                        onClick(weatherInfo.shortWeatherInfo.city)
//                        }
                        isSwipedBackToCard = false
                    }

                    true
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "$day - $description",
                fontSize = textSize
            )
            Text(
                text = "$maxTemp° / $minTemp°",
                fontSize = textSize
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}