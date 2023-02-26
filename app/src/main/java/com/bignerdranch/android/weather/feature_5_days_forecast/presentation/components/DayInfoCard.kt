package com.bignerdranch.android.weather.feature_5_days_forecast.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.weather.core.extensions.dayName
import com.bignerdranch.android.weather.core.log
import com.bignerdranch.android.weather.core.model.WeatherInfo
import kotlin.math.abs


@Composable
fun DayInfoCard(
    weatherInfo: WeatherInfo,
    nextDayWeatherInfo: WeatherInfo?,
    canvasHeight: Int, //dp
    unitHeight: Double, //dp
    circleRadius: Int,
    circleStroke: Int,
    maxTemp: Double,
    minTemp: Double
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var largerText by remember { mutableStateOf(0f) }
        Column( // Column for day and icon
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .height(64.dp)
                .background(
                    color = Color(0x39979797)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dayName(weatherInfo.date.day),
                modifier = Modifier
                    .onGloballyPositioned {
                        if(largerText < it.boundsInWindow().size.width)
                            largerText = it.boundsInWindow().size.width
                    }
            )
            Text(
                text = "${weatherInfo.date.day}/${weatherInfo.date.month}",
                modifier = Modifier
                    .onGloballyPositioned {
                        if(largerText < it.boundsInWindow().size.width)
                            largerText = it.boundsInWindow().size.width
                    }
            )
            if(weatherInfo.icon != null)
                Image(
                    bitmap = weatherInfo.icon.asImageBitmap(),
                    modifier = Modifier
                        .width((largerText / LocalDensity.current.density).dp),
                    contentDescription = "Daily Weather Icon",
                    contentScale = ContentScale.Inside
                )
        }
        Canvas(
            modifier = Modifier
                .height(canvasHeight.dp)
                .border(
                    width = 12.dp,
                    color = Color.Red
                )
        ) {
//            log("width: ${this.size.width}, height: ${this.size.height}")
            drawRect(
                color = Color.Green,
                topLeft = Offset(0f, 0f),
                size = Size(30f, 30f)
            )
            val centerX = this.size.width / 2 //horizontal center of canvas
            val countFromMin = abs(minTemp - weatherInfo.maxTempInCelsius)
            val circleYMax = this.size.height - countFromMin*unitHeight.dp.toPx()
            log("canvasHeight: ${this.size.height}, countFromMin: $countFromMin, " +
                    "unitHeightPx: ${unitHeight.dp.toPx()}, temp: ${weatherInfo.maxTempInCelsius}" +
                    " circleYMax: $circleYMax")
            drawCircle(
                center = Offset(centerX, circleYMax.toFloat()),
                radius = circleRadius.dp.toPx(),
                color = Color.White,
                style = Stroke(
                    width = circleStroke.dp.toPx()
                )
            )
        }
    }
}