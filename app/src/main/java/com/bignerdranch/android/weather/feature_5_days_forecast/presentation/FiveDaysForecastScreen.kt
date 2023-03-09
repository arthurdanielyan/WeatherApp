package com.bignerdranch.android.weather.feature_5_days_forecast.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bignerdranch.android.weather.core.presentation.components.TopBar
import com.bignerdranch.android.weather.feature_5_days_forecast.presentation.components.DayInfoCard
import com.bignerdranch.android.weather.feature_city_weather.presentation.components.ClickableIcon

@Composable
fun FiveDaysForecastScreen(
    viewModel: FiveDaysForecastViewModel,
    navController: NavController
) {
    val forecastState = viewModel.fiveDaysForecastState.collectAsState()
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        TopBar(
            screenTitle = "5-day forecast",
            navController = navController
        )
        if (forecastState.value.list != null) {
            val extremePoints = forecastState.value.list!!.forecastDays
            val maxTemp by remember {
                var max = Double.MIN_VALUE
                extremePoints.forEach {
                    if (it.maxTempInCelsius > max) {
                        max = it.maxTempInCelsius
                    }
                }
                mutableStateOf(max)
            }
            val minTemp by remember {
                var min = Double.MAX_VALUE
                extremePoints.forEach {
                    if (it.minTempInCelsius < min) {
                        min = it.minTempInCelsius
                    }
                }
                mutableStateOf(min)
            }


            val canvasHeight by remember { mutableStateOf(200) }
            val unitCount by remember { mutableStateOf(maxTemp - minTemp) }
            val unitHeight by remember { mutableStateOf(canvasHeight / unitCount) }
            val circleRadius by remember { mutableStateOf(5) }
            val graphStroke by remember { mutableStateOf(2) }
            var cardWidth by remember { mutableStateOf(1f) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp)
                    .onGloballyPositioned {
                        cardWidth = it.boundsInWindow().width / 5
                    },
                horizontalArrangement = Arrangement.Center,

                ) {
                val days = forecastState.value.list!!.forecastDays
                days.forEachIndexed { index, weatherInfo ->
                    DayInfoCard(
                        weatherInfo = weatherInfo,
                        nextDayWeatherInfo = try {
                            days[index + 1]
                        } catch (e: IndexOutOfBoundsException) {
                            null
                        },
                        previousDayWeatherInfo = try {
                            days[index - 1]
                        } catch (e: IndexOutOfBoundsException) {
                            null
                        },
                        canvasHeight = canvasHeight,
                        unitHeight = unitHeight,
                        circleRadius = circleRadius,
                        graphStroke = graphStroke,
                        cardWidth = cardWidth,
                        minTemp = minTemp
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}