package com.bignerdranch.android.weather.feature_city_weather.presentation

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.navigation.NavController
import com.bignerdranch.android.weather.core.extensions.toIntIfPossible
import com.bignerdranch.android.weather.core.presentation.Screen
import com.bignerdranch.android.weather.feature_city_weather.domain.model.HourForecast
import com.bignerdranch.android.weather.feature_city_weather.presentation.components.ClickableIcon
import com.bignerdranch.android.weather.feature_city_weather.presentation.components.ExtremePointsWeatherCard
import com.bignerdranch.android.weather.feature_city_weather.presentation.components.HourForecast
import com.bignerdranch.android.weather.feature_city_weather.presentation.state_wrappers.ScreenEvent
import com.bignerdranch.android.weather.ui.theme.defaultGradientEnd
import com.bignerdranch.android.weather.ui.theme.defaultGradientStart
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

const val ADD_BUTTON_ID = "add_button"
const val CITY_TEXT_BOX_ID = "city_text"
const val REFRESH_BUTTON_ID = "refresh_button_id"
const val COUNTRY_TEXT_ID = "country_button_id"

@Composable
fun CityWeatherScreen(
    viewModel: CityWeatherViewModel,
    navController: NavController
) {
    val weatherState = viewModel.currentWeatherState.collectAsState().value

    val constraintsTopBar = ConstraintSet {
        val addButton = createRefFor(ADD_BUTTON_ID)
        val cityText = createRefFor(CITY_TEXT_BOX_ID)
        val refreshButton = createRefFor(REFRESH_BUTTON_ID)
        val countryText = createRefFor(COUNTRY_TEXT_ID)

        constrain(addButton) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }

        constrain(refreshButton) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }

        constrain(cityText) {
            top.linkTo(parent.top)
            bottom.linkTo(countryText.top)
            start.linkTo(addButton.end)
            end.linkTo(refreshButton.start)
        }

        constrain(countryText) {
            top.linkTo(cityText.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }


    val context = LocalContext.current

    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        var toast: Toast? = null
        scope.launch {
            viewModel.screenEvents.collectLatest { event ->
                when (event) {
                    is ScreenEvent.ShowToast -> {
                        toast?.cancel()
                        toast = Toast.makeText(context, event.message, Toast.LENGTH_SHORT)
                        toast?.show()
                    }
                    else -> {}
                }
            }
        }
    }

    val isRefreshing by viewModel.shortForecastState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
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
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing.isLoading),
            onRefresh = {
                weatherState.value?.city?.let {
                    viewModel.getCityWeather(it)
                }
            },
            indicator = { state, trigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = trigger,
                    scale = true,
                    backgroundColor = MaterialTheme.colors.defaultGradientEnd,
                    shape = CircleShape,
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
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
                                onClick = {
                                    viewModel.saveCity()
                                }
                            )
                            Box(modifier = Modifier
                                .layoutId(CITY_TEXT_BOX_ID)
                                .padding(horizontal = 6.dp)
                            ) {
                                Text(
                                    text = weatherState.value?.city ?: "Loading…",
                                    style = MaterialTheme.typography.h4,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
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
                                }
                            )
                        }
                    }
                    Column(
                        // temp and description column
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
                                day = shortForecast.forecastDays[0].dayName
                            )
                            ExtremePointsWeatherCard(
                                minTemp = shortForecast.forecastDays[1].minTempInCelsius,
                                maxTemp = shortForecast.forecastDays[1].maxTempInCelsius,
                                description = shortForecast.forecastDays[1].description,
                                day = shortForecast.forecastDays[1].dayName
                            )
                            ExtremePointsWeatherCard(
                                minTemp = shortForecast.forecastDays[2].minTempInCelsius,
                                maxTemp = shortForecast.forecastDays[2].maxTempInCelsius,
                                description = shortForecast.forecastDays[2].description,
                                day = shortForecast.forecastDays[2].dayName
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .background(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(MaterialTheme.colors.defaultGradientEnd.toArgb()),
                                        Color(MaterialTheme.colors.defaultGradientStart.toArgb())
                                    ),
                                    start = Offset.Zero,
                                    end = Offset.Infinite
                                ),
                                shape = RoundedCornerShape(9999.dp)
                            )
                            .clip(RoundedCornerShape(9999.dp))
                            .clickable {
                                if (weatherState.value != null)
                                    navController.navigate(Screen.FiveDaysForecast.route + "/${weatherState.value.city}") {
                                        popUpTo(Screen.CityWeatherScreen.route)
                                    }
                            }
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) { Text("5-day forecast") } // 5-day forecast button
                }
                val hourlyForecastState by viewModel.hourlyForecastState.collectAsState()
                if(!hourlyForecastState.isLoading) {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        items(hourlyForecastState.list ?: emptyList()) { item: HourForecast ->
                            HourForecast(item)
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFF1F3C88)
                        )
                    }
                }
            }
        }
    }
}