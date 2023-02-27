package com.bignerdranch.android.weather.feature_5_days_forecast.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weather.core.extensions.dayName
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
    cardWidth: Float,
    minTemp: Double
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                color = Color(0xFF2C2C2C),
                shape = RoundedCornerShape(18.dp)
            )
            .width(LocalDensity.current.run { cardWidth.toDp() })
    ) {
        Column( // Column for day and icon
            modifier = Modifier
                .padding(
                    start = 5.dp,
                    end = 5.dp,
                    top = 12.dp
                )
                .height(64.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dayName(weatherInfo.date.day),
                fontSize = 15.sp
            )
            Text(
                text = "${weatherInfo.date.day}/${weatherInfo.date.month}",
            )
            if(weatherInfo.icon != null)
                Image(
                    bitmap = weatherInfo.icon.asImageBitmap(),
                    modifier = Modifier
                        .width(10.dp),
                    contentDescription = "Daily Weather Icon",
                    contentScale = ContentScale.Inside
                )
        }
        Spacer(modifier = Modifier.height(28.dp))
        Canvas(
            modifier = Modifier
                .height(canvasHeight.dp)
                .border(
                    width = 12.dp,
                    color = Color.Red
                )
        ) {
            drawRect(
                color = Color.Green,
                topLeft = Offset(0f, 0f),
                size = Size(30f, 30f)
            )
            val centerX = this.size.width / 2 //horizontal center of canvas
            val countFromMin = abs(minTemp - weatherInfo.maxTempInCelsius)
            val circleYMax = this.size.height - countFromMin*unitHeight.dp.toPx()
            drawCircle(
                center = Offset(centerX, circleYMax.toFloat()),
                radius = circleRadius.dp.toPx(),
                color = Color.White,
                style = Stroke(
                    width = circleStroke.dp.toPx()
                )
            )
            if (nextDayWeatherInfo != null) {
                val nextCountFromMin = abs(minTemp - nextDayWeatherInfo.maxTempInCelsius)
                val nextCircleYMax = this.size.height - nextCountFromMin.dp.toPx()
//                val direction = Offset()
//                drawLine(
//                    color = Color.White,
//
//                )
            }
        }
    }
}