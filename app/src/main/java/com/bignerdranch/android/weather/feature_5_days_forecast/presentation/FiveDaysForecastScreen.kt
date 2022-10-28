package com.bignerdranch.android.weather.feature_5_days_forecast.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weather.core.extensions.normalized
import com.bignerdranch.android.weather.core.extensions.toIntIfPossible
import com.bignerdranch.android.weather.feature_5_days_forecast.presentation.componentes.DayInfo
import kotlin.math.roundToInt

@OptIn(ExperimentalTextApi::class)
@Composable
fun FiveDaysForecastScreen(
    viewModel: FiveDaysForecastViewModel
) {
    val forecastState = viewModel.fiveDaysForecastState.collectAsState()
    if(forecastState.value.list != null) {
        val extremePoints = forecastState.value.list!!.forecastDays
        val maxTemp by remember {
            var max = Int.MIN_VALUE
            extremePoints.forEach {
                if (it.maxTempInCelsius > max){
                    max = it.maxTempInCelsius.roundToInt()
                }
            }
            mutableStateOf(max)
        }
        val minTemp by remember {
            var min = Int.MAX_VALUE
            extremePoints.forEach {
                if (it.minTempInCelsius < min){
                    min = it.minTempInCelsius.roundToInt()
                }
            }
            mutableStateOf(min)
        }


        val height by remember { mutableStateOf(200) }
        val unitCount by remember { mutableStateOf(maxTemp - minTemp) }
        val unitHeight by remember { mutableStateOf(height / unitCount) }
        val circleRadius by remember { mutableStateOf(5) }
        val circleStroke by remember { mutableStateOf(2) }
        val verticalPadding by remember { mutableStateOf(50) }
        val textMeasurer = rememberTextMeasurer()

        Column {
            Text(
                text = "5-day forecast",
                style = MaterialTheme.typography.h4,
                color = Color.White
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(36.dp)
                    .background(MaterialTheme.colors.background)
            ) {
                val days = forecastState.value.list!!.forecastDays
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (day in days) {
                        DayInfo(
                            date = day.date,
                            icon = day.icon
                        )
                    }
                }
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height.dp + verticalPadding.dp * 2)
                ) {

                    drawRect(
                        topLeft = Offset(0f, 0f),
                        size = Size(size.width, size.height),
                        color = Color.Red
                    )
                    val maxPoints: MutableList<Offset> = mutableListOf()
                    val minPoints: MutableList<Offset> = mutableListOf()
                    extremePoints.forEachIndexed { index, extremePoints ->
                        val x = index * (this.size.width / 4)

                        // drawing max temp circles
                        val fromMin = extremePoints.maxTempInCelsius - minTemp
                        val yFromMin = (fromMin * unitHeight).dp.toPx()
                        val y = height.dp.toPx() - yFromMin + verticalPadding.dp.toPx()
                        drawCircle(
                            center = Offset(x, y),
                            radius = circleRadius.dp.toPx(),
                            color = Color.White,
                            style = Stroke(
                                width = circleStroke.dp.toPx()
                            )
                        )
                        drawText(
                            textMeasurer = textMeasurer,
                            text = "${extremePoints.maxTempInCelsius.toIntIfPossible()}°",
                            topLeft = Offset(
                                (x - 20.dp.toPx()),
                                y - 36.dp.toPx()
                            ),
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 17.sp
                            ),
                            overflow = TextOverflow.Visible,
                            softWrap = false
                        )
                        // drawing min temp circles
                        val fromMin2 = extremePoints.minTempInCelsius.roundToInt() - minTemp
                        val yFromMin2 = (fromMin2 * unitHeight).dp.toPx()
                        val y2 = height.dp.toPx() - yFromMin2 + verticalPadding.dp.toPx()
                        drawCircle(
                            center = Offset(x, y2),
                            radius = circleRadius.dp.toPx(),
                            color = Color.White,
                            style = Stroke(
                                width = circleStroke.dp.toPx()
                            )
                        )
                        drawText(
                            textMeasurer = textMeasurer,
                            text = "${extremePoints.minTempInCelsius.toIntIfPossible()}°",
                            topLeft = Offset(
                                (x - 15.dp.toPx()),
                                y2 + 10.dp.toPx()
                            ),
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 17.sp
                            ),
                            overflow = TextOverflow.Visible,
                            softWrap = false
                        )
                        maxPoints.add(Offset(x, y))
                        minPoints.add(Offset(x, y2))
                    }
                    for (i in 0..minPoints.size - 2) {
                        drawLine(
                            start = minPoints[i] + ((minPoints[i + 1] - minPoints[i]).normalized() * (circleRadius.dp.toPx())),
                            end = minPoints[i + 1] + ((minPoints[i] - minPoints[i + 1]).normalized() * (circleRadius.dp.toPx())),
                            color = Color.White,
                            strokeWidth = 2.dp.toPx()
                        )
                        drawLine(
                            start = maxPoints[i] + ((maxPoints[i + 1] - maxPoints[i]).normalized() * (circleRadius.dp.toPx())),
                            end = maxPoints[i + 1] + ((maxPoints[i] - maxPoints[i + 1]).normalized() * (circleRadius.dp.toPx())),
                            color = Color.White,
                            strokeWidth = 2.dp.toPx()
                        )
                    }
                }
            }
        }
    }
}