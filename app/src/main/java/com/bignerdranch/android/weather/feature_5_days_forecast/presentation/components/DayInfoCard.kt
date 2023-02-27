package com.bignerdranch.android.weather.feature_5_days_forecast.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weather.core.extensions.dayName
import com.bignerdranch.android.weather.core.extensions.normalized
import com.bignerdranch.android.weather.core.model.WeatherInfo
import kotlin.math.abs


@Composable
fun DayInfoCard(
    weatherInfo: WeatherInfo,
    nextDayWeatherInfo: WeatherInfo?,
    previousDayWeatherInfo: WeatherInfo?,
    canvasHeight: Int, //dp
    unitHeight: Double, //dp
    circleRadius: Int,  // in dp
    graphStroke: Int,  //in dp
    cardWidth: Float, // in pxs
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
                .height(90.dp),//must be a fixed height so that the top of the canvas would be a straight line
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dayName(weatherInfo.date.day),
                fontSize = 15.sp
            )
            Text(
                text = "${weatherInfo.date.day}/${weatherInfo.date.month}",
            )
            if(weatherInfo.icon != null) {
                Spacer(modifier = Modifier.height(6.dp))
                Image(
                    bitmap = weatherInfo.icon.asImageBitmap(),
                    modifier = Modifier
                        .size(LocalDensity.current.run { (0.45f*cardWidth).toDp() }),
                    contentDescription = "Daily Weather Icon",
                    contentScale = ContentScale.Fit
                )
            }
        }
        Spacer(modifier = Modifier.height(60.dp))
        Canvas(
            modifier = Modifier
                .height(canvasHeight.dp)
        ) {
            val centerX = this.size.width / 2 //horizontal center of canvas
            val countFromMin = abs(minTemp - weatherInfo.maxTempInCelsius)
            val circleYMax = this.size.height - countFromMin*unitHeight.dp.toPx()
            drawCircle(
                center = Offset(centerX, circleYMax.toFloat()),
                radius = circleRadius.dp.toPx(),
                color = Color.White,
                style = Stroke(
                    width = graphStroke.dp.toPx()
                )
            )

            if (nextDayWeatherInfo != null) {
                val nextCountFromMin = abs(minTemp - nextDayWeatherInfo.maxTempInCelsius)
                val nextCircleYMax = (this.size.height - nextCountFromMin*unitHeight.dp.toPx()).toFloat()
                val direction = Offset(cardWidth, nextCircleYMax-circleYMax.toFloat()).normalized()
                drawLine(
                    color = Color.White,
                    start = Offset(0f, circleYMax.toFloat())+direction*(circleRadius.dp.toPx()),
                    end = Offset(cardWidth, nextCircleYMax),
                    strokeWidth = graphStroke.dp.toPx()
                )
            }
            if(previousDayWeatherInfo != null) {
                val previousCountFromMin = abs(minTemp - previousDayWeatherInfo.maxTempInCelsius)
                val previousCircleYMax = (this.size.height - previousCountFromMin*unitHeight.dp.toPx()).toFloat()
                val direction = Offset(-cardWidth, previousCircleYMax-circleYMax.toFloat()).normalized()
                drawLine(
                    color = Color.White,
                    start = Offset(0f, circleYMax.toFloat())+direction*(circleRadius.dp.toPx()),
                    end = Offset(-cardWidth, previousCircleYMax)-direction*circleRadius.dp.toPx(),
                    strokeWidth = graphStroke.dp.toPx()
                )
                /** Why canvas can draw at the left side outside of its box, but not at the right side?
                 * Because in the row items are put on top of each other?*/
            }
        }
    }
}