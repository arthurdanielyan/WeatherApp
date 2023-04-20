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
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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
    val scrolledLettersRanged = scrolledLetters.putInRange(0, text.length - 2)
    val actualText = text.substring(scrolledLettersRanged)
    var lastTouchX by remember { mutableStateOf(0f) }
    var textWidth by remember { mutableStateOf(0.dp) }
    var rowWidth by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current

    Row(
        modifier = modifier
            .pointerInteropFilter { motionEvent ->
                when (motionEvent.action) {
                    ACTION_DOWN -> {
                        initialX = motionEvent.x
                    }
                    ACTION_MOVE -> {
                        val currentX = motionEvent.x
                        val swipedLeft = currentX - lastTouchX < 0
                        lastTouchX = currentX
                        log(
                            try {
                                initialX.toInt() % currentX.toInt()
                            } catch (e: ArithmeticException) {
                                e.localizedMessage
                            }
                        )
                        log("scrolledLetterRanged: $scrolledLettersRanged, actualText: $actualText, text: $text")
                        if ((initialX - currentX).toInt() % 3 == 0)
                            if (swipedLeft) {
                                if(textWidth >= rowWidth - 5.dp)
                                scrolledLetters++
                                log("scrolled forward $scrolledLetters")
                            } else {
                                scrolledLetters--
                                log("scrolled backward $scrolledLetters")
                            }
                    }
                }
                true
            }
            .onGloballyPositioned {
                rowWidth = density.run { it.boundsInParent().size.width.toDp() }
            },
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = textModifier,
            text = actualText,
            maxLines = 1,
            style = style,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { layoutResult ->
                textWidth = density.run { layoutResult.size.width.toDp() }
            }
        )
    }
}