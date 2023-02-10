package com.bignerdranch.android.weather.core.extensions

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.scalingClickAnimation(
    clickScale: Float = 0.9f,
    durationMillis: Int = 100,
    onClick: () -> Unit = {}
) = composed {
    var targetScale by remember { mutableStateOf(1f) }
    val touchAnimation by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = tween(
            durationMillis = durationMillis
        )
    )
    var cardWidth by remember { mutableStateOf(0f) }
    var cardHeight by remember { mutableStateOf(0f) }
    var isSwipedBackToCard by remember { mutableStateOf(false) }

    this.onGloballyPositioned {
        val windowBounds = it.boundsInParent()
        cardWidth = windowBounds.size.width
        cardHeight = windowBounds.size.height
    }
        .pointerInteropFilter { touch: MotionEvent ->
            if(touch.x in 0f..cardWidth && touch.y in 0f..cardHeight && !isSwipedBackToCard) {
                targetScale = clickScale
                isSwipedBackToCard = false
            }
            else {
                targetScale = 1f
                isSwipedBackToCard = true
            }

            if(touch.actionMasked == MotionEvent.ACTION_UP) {
                targetScale = 1f
                if(touch.x in 0f..cardWidth && touch.y in 0f..cardHeight && !isSwipedBackToCard) {
                    onClick()
                }
                isSwipedBackToCard = false
            } else if(touch.actionMasked == MotionEvent.ACTION_CANCEL) {
                targetScale = 1f
            }

            true
        }
        .scale(touchAnimation)
}