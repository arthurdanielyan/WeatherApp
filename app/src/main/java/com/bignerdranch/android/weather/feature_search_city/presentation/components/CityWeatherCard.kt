package com.bignerdranch.android.weather.feature_search_city.presentation.components

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weather.core.extensions.toIntIfPossible
import com.bignerdranch.android.weather.core.log
import com.bignerdranch.android.weather.feature_search_city.domain.model.ShortWeatherInfo
import com.bignerdranch.android.weather.ui.theme.defaultGradientEnd
import com.bignerdranch.android.weather.ui.theme.defaultGradientStart

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CityWeatherCard(
    modifier: Modifier = Modifier,
    weatherInfo: ShortWeatherInfo,
    onClick: (/*city: String*/) -> Unit
) {
    val touchScale = 0.9f
    var targetScale by remember { mutableStateOf(1f) }
    val touchAnimation by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = tween(
            durationMillis = 100
        )
    )
    var cardWidth by remember { mutableStateOf(0f) }
    var cardHeight by remember { mutableStateOf(0f) }
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colors.background,
            )
            .fillMaxWidth()
            .scale(touchAnimation)
    ) {
        var isSwipedBackToCard by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(MaterialTheme.colors.defaultGradientStart.toArgb()),
                            Color(MaterialTheme.colors.defaultGradientEnd.toArgb())
                        ),
                        start = Offset.Zero,
                        end = Offset.Infinite
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(8.dp)
                .onGloballyPositioned {
                    val windowBounds = it.boundsInParent()
                    cardWidth = windowBounds.size.width
                    cardHeight = windowBounds.size.height
                }
                .pointerInteropFilter { touch: MotionEvent ->
                    log("touch")
                    if(touch.x in 0f..cardWidth && touch.y in 0f..cardHeight && !isSwipedBackToCard) {
                        targetScale = touchScale
                        isSwipedBackToCard = false
                        log("touch inside")
                    }
                    else {
                        targetScale = 1f
                        isSwipedBackToCard = true
                        log("touch outside")
                    }
                    log("$cardWidth $cardHeight")

                    log(touch.actionMasked)
                    if(touch.actionMasked == MotionEvent.ACTION_UP) {
                        targetScale = 1f
                        if(touch.x in 0f..cardWidth && touch.y in 0f..cardHeight && !isSwipedBackToCard) {
                            onClick(/*weatherInfo.city*/)
                            log("onClick")
                        }
                        isSwipedBackToCard = false
                    } else if(touch.actionMasked == MotionEvent.ACTION_CANCEL) {
                        targetScale = 1f
//                        isSwipedBackToCard = false
                    }

                    true
                }
        ) {
            Text(
                text = weatherInfo.city,
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = weatherInfo.country,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "${weatherInfo.tempInCelsius.toIntIfPossible()} °C",
                    fontSize = 20.sp,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}