package com.bignerdranch.android.weather.feature_5_days_forecast.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.bignerdranch.android.weather.feature_5_days_forecast.presentation.state_wrappers.ExtremePointsHolder

@Composable
fun FiveDaysForecastScreen(
    extremePoints: List<ExtremePointsHolder> = ExtremePointsHolder.demoExtremePoints
) {
    val maxTemp by remember {
        var max = Int.MIN_VALUE
        extremePoints.forEach {
            if (it.maxCelsius > max){
                max = it.maxCelsius
            }
        }
        mutableStateOf(max)
    }
    val minTemp by remember {
        var min = Int.MAX_VALUE
        extremePoints.forEach {
            if (it.minCelsius < min){
                min = it.minCelsius
            }
        }
        mutableStateOf(min)
    }


    val height by remember { mutableStateOf(180) }
    val unitCount by remember { mutableStateOf(maxTemp - minTemp) }
    val unitHeight by remember { mutableStateOf(height / unitCount) }
    val circleRadius by remember { mutableStateOf(5) }
    val circleStroke by remember { mutableStateOf(2) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(36.dp)
            .background(MaterialTheme.colors.background)
    ) {
        Text(
            text = "5-day forecast",
            style = MaterialTheme.typography.h4,
            color = Color.White
        )
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 50.dp)
                .height(height.dp)
        ) {
            drawRect(
                topLeft = Offset(0f,0f),
                size = Size(size.width, size.height),
                color = Color.Red
            )
            val minPoints: MutableList<Offset> = mutableListOf()
            val maxPoints: MutableList<Offset> = mutableListOf()
            extremePoints.forEachIndexed { index, extremePoints ->
                val x = index*(this.size.width / 4)

                // drawing max temp circles
                val fromMin = extremePoints.maxCelsius - minTemp
                val yFromMin = (fromMin * unitHeight).dp.toPx()
                val y = this.size.height - yFromMin
                drawCircle(
                    center = Offset(x, y),
                    radius = circleRadius.dp.toPx(),
                    color = Color.White,
                    style = Stroke(
                        width = circleStroke.dp.toPx()
                    )
                )
                // drawing min temp circles
                val fromMin2 = extremePoints.minCelsius - minTemp
                val yFromMin2 = (fromMin2 * unitHeight).dp.toPx()
                val y2 = this.size.height - yFromMin2
                drawCircle(
                    center = Offset(x, y2),
                    radius = circleRadius.dp.toPx(),
                    color = Color.White,
                    style = Stroke(
                        width = circleStroke.dp.toPx()
                    )
                )

                minPoints.add(Offset(x, y))
                maxPoints.add(Offset(x, y2))
            }
            for(i in 0..minPoints.size-2) {
                drawLine(
                    start = minPoints[i] + ((minPoints[i+1] - minPoints[i]).normalized() * (circleRadius.dp.toPx())),
                    end = minPoints[i+1] + ((minPoints[i] - minPoints[i+1]).normalized() * (circleRadius.dp.toPx())),
                    color = Color.White,
                    strokeWidth = 2.dp.toPx()
                )
                drawLine(
                    start = maxPoints[i] + ((maxPoints[i+1] - maxPoints[i]).normalized() * (circleRadius.dp.toPx())),
                    end = maxPoints[i+1] + ((maxPoints[i] - maxPoints[i+1]).normalized() * (circleRadius.dp.toPx())),
                    color = Color.White,
                    strokeWidth = 2.dp.toPx()
                )
            }
        }
    }
}


fun Offset.normalized(): Offset {
    val length = this.getDistance()
    return Offset(this.x / length, this.y / length)
}

operator fun Offset.times(f: Number) =
    Offset(this.x*f.toFloat(), this.y*f.toFloat())

