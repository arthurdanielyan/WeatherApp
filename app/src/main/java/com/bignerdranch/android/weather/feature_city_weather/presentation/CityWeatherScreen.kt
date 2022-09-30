package com.bignerdranch.android.weather.feature_city_weather.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.bignerdranch.android.weather.core.extensions.toIntIfPossible
import com.bignerdranch.android.weather.core.log
import com.bignerdranch.android.weather.feature_city_weather.presentation.components.ClickableIcon
import com.bignerdranch.android.weather.feature_city_weather.presentation.components.ExtremePointsWeatherCard
import com.bignerdranch.android.weather.ui.theme.defaultGradientEnd
import com.bignerdranch.android.weather.ui.theme.defaultGradientStart
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlin.math.roundToInt

//@Preview(showBackground = true)
//@Composable
//fun CityWeatherScreenPreview() {
//    WeatherTheme {
//        Surface(
//            modifier = Modifier
//                .fillMaxSize(),
//            color = MaterialTheme.colors.background
//        ) {
//            CityWeatherScreen("")
//        }
//    }
//}

const val ADD_BUTTON_ID = "add_button"
const val CITY_TEXT_ID = "city_text"
const val REFRESH_BUTTON_ID = "refresh_button_id"
const val COUNTRY_TEXT_ID = "country_button_id"

@Composable
fun CityWeatherScreen(
    viewModel: CityWeatherViewModel
) {
    val weatherState = viewModel.currentWeatherState.collectAsState().value

    val constraintsTopBar = ConstraintSet {
        val addButton = createRefFor(ADD_BUTTON_ID)
        val cityText = createRefFor(CITY_TEXT_ID)
        val refreshButton = createRefFor(REFRESH_BUTTON_ID)
        val countryText = createRefFor(COUNTRY_TEXT_ID)

        constrain(addButton) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }

        constrain(cityText) {
            top.linkTo(parent.top)
            bottom.linkTo(countryText.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(countryText) {
            top.linkTo(cityText.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(refreshButton) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }
    }

    val isRefreshing by viewModel.shortForecastState.collectAsState()
    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing.isLoading),
        onRefresh = {
            weatherState.value?.city?.let {
                viewModel.getCityWeather(it)
            }
        },
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                // Pass the SwipeRefreshState + trigger through
                state = state,
                refreshTriggerDistance = trigger,
                // Enable the scale animation
                scale = true,
                // Change the color and shape
                backgroundColor = MaterialTheme.colors.defaultGradientEnd,
                shape = CircleShape,
            )
        }
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(MaterialTheme.colors.defaultGradientStart.toArgb()),
                            Color(MaterialTheme.colors.defaultGradientEnd.toArgb())
                        ),
                        start = Offset.Zero,
                        end = Offset.Infinite
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Row( // top bar row
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxWidth(),
                        constraintSet = constraintsTopBar
                    ) {
                        ClickableIcon(
                            modifier = Modifier
                                .layoutId(ADD_BUTTON_ID),
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add city",
                            onClick = {}
                        )
                        Text(
                            modifier = Modifier
                                .layoutId(CITY_TEXT_ID),
                            text = weatherState.value?.city ?: "Loading…",
                            style = MaterialTheme.typography.h4
                        )
                        Text(
                            modifier = Modifier
                                .layoutId(COUNTRY_TEXT_ID)
                                .padding(top = 8.dp),
                            text = weatherState.value?.country ?: "Loading…",
                            fontSize = 15.sp
                        )
                        ClickableIcon(
                            modifier = Modifier
                                .layoutId(REFRESH_BUTTON_ID),
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reload",
                            onClick = {
                                weatherState.value?.city?.let {
                                    viewModel.getCityWeather(it)
                                }
                                log("updating")
                            }
                        )
                    }
                }

                Column(
                    // temp and desc column
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    if (weatherState.value != null) {
                        Spacer(Modifier.height(36.dp))
                        Text(
                            text = "${weatherState.value.tempInCelsius.toIntIfPossible()}°C",
                            style = MaterialTheme.typography.h1
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            var descTextHeight by remember { mutableStateOf(0) }
                            Text(
                                text = weatherState.value.description,
                                fontSize = 25.sp,
                                modifier = Modifier
                                    .onGloballyPositioned {
                                        descTextHeight =
                                            it.boundsInWindow().size.height.roundToInt()
                                    }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            val weatherIcon = viewModel.weatherIcon.collectAsState().value
                            if (weatherIcon.value != null)
                                Image(
                                    bitmap = weatherIcon.value.asImageBitmap(),
                                    contentDescription = weatherState.value.description,
                                    modifier = Modifier
                                        .height((descTextHeight / LocalDensity.current.density).dp - 8.dp),
                                    contentScale = ContentScale.Crop
                                )
                            else
                                CircularProgressIndicator(
                                    color = Color(0xFF1F3C88),
                                    modifier = Modifier
                                        .size((descTextHeight / LocalDensity.current.density).dp - 8.dp),
                                    strokeWidth = 3.dp
                                )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(36.dp))
                val shortForecastState by viewModel.shortForecastState.collectAsState()
                if (shortForecastState.value != null) {
                    val shortForecast = shortForecastState.value!!
                    Column( // 3 days column
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        ExtremePointsWeatherCard(
                            minTemp = shortForecast.forecastDays[0].minTempInCelsius,
                            maxTemp = shortForecast.forecastDays[0].maxTempInCelsius,
                            description = shortForecast.forecastDays[0].description,
                            day = shortForecast.forecastDays[0].day
                        )
                        ExtremePointsWeatherCard(
                            minTemp = shortForecast.forecastDays[1].minTempInCelsius,
                            maxTemp = shortForecast.forecastDays[1].maxTempInCelsius,
                            description = shortForecast.forecastDays[1].description,
                            day = shortForecast.forecastDays[1].day
                        )
                        ExtremePointsWeatherCard(
                            minTemp = shortForecast.forecastDays[2].minTempInCelsius,
                            maxTemp = shortForecast.forecastDays[2].maxTempInCelsius,
                            description = shortForecast.forecastDays[2].description,
                            day = shortForecast.forecastDays[2].day
                        )
                    }
                }
            }
        }
    }
}