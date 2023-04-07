package com.bignerdranch.android.weather.feature_city_weather.presentation.components

import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_MOVE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.bignerdranch.android.weather.core.constants.log
import com.bignerdranch.android.weather.core.extensions.putInRange

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ScrollableText(
    modifier: Modifier = Modifier,

    textModifier: Modifier = Modifier,
    text: String,
    style: TextStyle = LocalTextStyle.current
) {

    var scrolledLetters by remember { mutableStateOf(0) }
    var initialX by remember { mutableStateOf(0f) }
    val scrolledLettersRanged = scrolledLetters.putInRange(0, text.length-2)
    val actualText = text.substring(scrolledLettersRanged)
    var lastTouchX by remember { mutableStateOf(0f) }

    Row(
        modifier = modifier
            .pointerInteropFilter { motionEvent ->
                when(motionEvent.action) {
                    ACTION_DOWN -> {
                        initialX = motionEvent.x
                    }
                    ACTION_MOVE -> {
                        val currentX = motionEvent.x
                        val swipedLeft = currentX - lastTouchX < 0
                        lastTouchX = currentX
                        log(initialX.toInt() % currentX.toInt())
                        log("scrolledLetterRanged: $scrolledLettersRanged, actualText: $actualText, text: $text")
                        if((initialX.toInt() - currentX.toInt())%5 == 0)
                        if (swipedLeft) {
                            if(actualText == text.substring(text.length-actualText.length))
                                scrolledLetters++
                            log("scrolled forward $scrolledLetters")
                        } else {
                            scrolledLetters--
                            log("scrolled backward $scrolledLetters")
                        }
                    }
                }
                true
            },
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = textModifier,
            text = actualText,
            maxLines = 1,
            style = style,
            overflow = TextOverflow.Ellipsis,
        )
    }
}