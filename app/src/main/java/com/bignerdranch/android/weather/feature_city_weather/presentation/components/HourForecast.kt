package com.bignerdranch.android.weather.feature_city_weather.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weather.core.extensions.toIntIfPossible
import com.bignerdranch.android.weather.feature_city_weather.domain.model.HourForecast


@Composable
fun HourForecast(
    hourForecast: HourForecast
) {
    Column(
        modifier = Modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = hourForecast.time,
            fontSize = 12.sp,
            color = Color(0xFFB3B3B3)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = hourForecast.tempInCelsius.toIntIfPossible(),
            fontSize = 22.sp
        )
        Spacer(modifier = Modifier.height(6.dp))
        if(hourForecast.icon != null)
        Image(
            modifier = Modifier.size(20.dp),
            contentScale = ContentScale.Fit,
            bitmap = hourForecast.icon.asImageBitmap(),
            contentDescription = null,
        )
    }
}