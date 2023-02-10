package com.bignerdranch.android.weather.feature_city_weather.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.weather.feature_city_weather.domain.model.HourForecast


@Composable
fun HourForecast(
    hourForecast: HourForecast
) {
    Column(
        modifier = Modifier
    ) {
        Text(
            text = "${hourForecast.time}"
        )
        Text(
            text = "${hourForecast.tempInCelsius}"
        )
        if(hourForecast.icon != null)
        Icon(
            bitmap = hourForecast.icon!!.asImageBitmap(),
            contentDescription = null
        )
    }
}