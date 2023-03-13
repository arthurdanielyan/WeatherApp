package com.bignerdranch.android.weather.feature_city_weather.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weather.core.extensions.scalingClickAnimation
import com.bignerdranch.android.weather.core.model.WeatherInfo
import com.bignerdranch.android.weather.ui.theme.defaultGradientEnd
import com.bignerdranch.android.weather.ui.theme.defaultGradientStart

@Composable
fun ExtremePointsWeatherCard(
    modifier: Modifier = Modifier,
    weatherInfo: WeatherInfo
) {
    val textSize = 16.sp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scalingClickAnimation() // animation is beautiful
    ) {
        var tempsText by remember { mutableStateOf(0.dp) }
        var fullWidth by remember { mutableStateOf(0.dp) }
        val descText by remember(tempsText, fullWidth) { mutableStateOf(fullWidth - tempsText - 15.dp) }
        val density = LocalDensity.current
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
                .onGloballyPositioned { fullWidth = density.run { it.size.width.toDp() } },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.width(descText),
                text = "${weatherInfo.dayName} - ${weatherInfo.description} ",
                fontSize = textSize,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                modifier = Modifier.onGloballyPositioned { tempsText = density.run { it.size.width.toDp() } },
                text = "${weatherInfo.getMaxTempString()} / ${weatherInfo.getMinTempString()}",
                fontSize = textSize,
                maxLines = 1
            )
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}