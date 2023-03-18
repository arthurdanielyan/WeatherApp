package com.bignerdranch.android.weather.feature_search_city.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weather.core.extensions.scalingClickAnimation
import com.bignerdranch.android.weather.core.data.model.ShortWeatherInfo
import com.bignerdranch.android.weather.ui.theme.defaultGradientEnd
import com.bignerdranch.android.weather.ui.theme.defaultGradientStart

@Composable
fun CityWeatherCard(
    modifier: Modifier = Modifier,
    weatherInfo: ShortWeatherInfo,
    onClick: (city: String) -> Unit
) {

    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colors.background,
            )
            .fillMaxWidth()
            .scalingClickAnimation (
                onClick = { onClick(weatherInfo.city) }
            )
    ) {
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
                    text = weatherInfo.getTemp(),
                    fontSize = 20.sp,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}