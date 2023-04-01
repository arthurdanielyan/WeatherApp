package com.bignerdranch.android.weather.feature_5_days_forecast.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bignerdranch.android.weather.core.constants.log
import com.bignerdranch.android.weather.core.extensions.dayName
import com.bignerdranch.android.weather.core.extensions.normalized
import com.bignerdranch.android.weather.core.model.WeatherInfo
import kotlin.math.abs


@OptIn(ExperimentalTextApi::class)
@Composable
fun DayInfoCard(
    weatherInfo: WeatherInfo,
    previousDayWeatherInfo: WeatherInfo?,
    canvasHeight: Int, //dp
    unitHeight: Double, //dp
    circleRadius: Int,  // in dp
    graphStroke: Int,  //in dp
    cardWidth: Float, // in pxs
    minTemp: Double
) {
    val textMeasurer = rememberTextMeasurer()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(
                color = Color(0xFF2C2C2C),
                shape = RoundedCornerShape(18.dp)
            )
            .width(LocalDensity.current.run { cardWidth.toDp() })
            .padding(bottom = 36.dp)
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
            val density = LocalDensity.current
            var textSize by remember { mutableStateOf(15.sp) }
            Text(
                modifier = Modifier
                    .onSizeChanged {
                        if(it.width > cardWidth - 2*density.run { 5.dp.toPx() }) {
                            textSize = (textSize.value - 1).sp
                        }
                    },
                text = dayName(weatherInfo.date.day),
                fontSize = textSize,
                maxLines = 1
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
        val canvasBottomMargin by remember { mutableStateOf(17) } // used to make enough space for min temp texts
        val cardWidthInDp = LocalDensity.current.run {
            cardWidth.toDp()
        }
        Canvas(
            modifier = Modifier
                .height(canvasHeight.dp + canvasBottomMargin.dp)
        ) {
            val centerX = this.size.width / 2 //horizontal center of canvas
            val drawableCanvasHeight = (this.size.height - canvasBottomMargin.dp.toPx()) // excludes bottom space for min temp texts

            // START of drawing MAX points
            val countMaxFromMin = abs(minTemp - weatherInfo.getMaxTemp())
            val circleYMax = drawableCanvasHeight - countMaxFromMin*unitHeight.dp.toPx()
            drawCircle(
                center = Offset(centerX, circleYMax.toFloat()),
                radius = circleRadius.dp.toPx(),
                color = Color.White,
                style = Stroke(
                    width = graphStroke.dp.toPx()
                )
            )
            log((-cardWidthInDp))
            drawText(
                textMeasurer = textMeasurer,
                text = weatherInfo.getMaxTempString(),
                topLeft = Offset((-cardWidthInDp/2).toPx() + 8.dp.toPx(), (circleYMax - 36.dp.toPx()).toFloat()),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 17.sp
                ),
                overflow = TextOverflow.Visible,
                softWrap = false
            )
            if(previousDayWeatherInfo != null) {
                val previousCountFromMin = abs(minTemp - previousDayWeatherInfo.getMaxTemp())
                val previousCircleYMax = (drawableCanvasHeight - previousCountFromMin*unitHeight.dp.toPx()).toFloat()
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
            // END of drawing MAX points

            // START of drawing MIN points
            val countMinFromMin = abs(minTemp - weatherInfo.getMinTemp())
            val circleYMin = drawableCanvasHeight - countMinFromMin*unitHeight.dp.toPx()
            drawCircle(
                center = Offset(centerX, circleYMin.toFloat()),
                radius = circleRadius.dp.toPx(),
                color = Color.White,
                style = Stroke(
                    width = graphStroke.dp.toPx()
                )
            )
            drawText(
                textMeasurer = textMeasurer,
                text = weatherInfo.getMinTempString(),
                topLeft = Offset((-cardWidthInDp / 2.5f).toPx(), (circleYMin + 13.dp.toPx()).toFloat()),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 17.sp
                ),
                overflow = TextOverflow.Visible,
                softWrap = false
            )

            if(previousDayWeatherInfo != null) {
                val previousCountFromMin = abs(minTemp - previousDayWeatherInfo.getMinTemp())
                val previousCircleYMin = (drawableCanvasHeight - previousCountFromMin*unitHeight.dp.toPx()).toFloat()
                val direction = Offset(-cardWidth, previousCircleYMin-circleYMin.toFloat()).normalized()
                drawLine(
                    color = Color.White,
                    start = Offset(0f, circleYMin.toFloat())+direction*(circleRadius.dp.toPx()),
                    end = Offset(-cardWidth, previousCircleYMin)-direction*circleRadius.dp.toPx(),
                    strokeWidth = graphStroke.dp.toPx()
                )
            }
            // END of drawing MIN points
        }
    }
}