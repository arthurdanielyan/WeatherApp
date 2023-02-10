package com.bignerdranch.android.weather.feature_city_weather.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weather.core.extensions.scalingClickAnimation
import com.bignerdranch.android.weather.ui.theme.defaultGradientEnd
import com.bignerdranch.android.weather.ui.theme.defaultGradientStart

@Composable
fun ExtremePointsWeatherCard(
    modifier: Modifier = Modifier,
    minTemp: Double,
    maxTemp: Double,
    description: String,
    day: String
) {
    val textSize = 16.sp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scalingClickAnimation() // animation is beautiful
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
                .padding(19.dp),
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