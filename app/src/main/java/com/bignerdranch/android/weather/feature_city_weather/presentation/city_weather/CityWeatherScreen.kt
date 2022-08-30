package com.bignerdranch.android.weather.feature_city_weather.presentation.city_weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.bignerdranch.android.weather.ui.theme.WeatherTheme
import org.koin.androidx.compose.getViewModel

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CityWeatherScreenPreview() {
    WeatherTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            CityWeatherScreen("")
        }
    }
}

const val ADD_BUTTON_ID = "add_button"
const val CITY_TEXT_ID = "city_text"
const val REFRESH_BUTTON_ID = "refresh_button_id"
const val COUNTRY_TEXT_ID = "country_button_id"

@Composable
fun CityWeatherScreen(
    city: String,
    viewModel: CityWeatherViewModel = getViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getCityWeather(city)
    }

    val weatherState = viewModel.state.value

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

    Box(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF749BFF),
                        Color(0xFF003DDA)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxWidth(),
                    constraintSet = constraintsTopBar
                ) {
                    Icon(
                        modifier = Modifier
                            .layoutId(ADD_BUTTON_ID),
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add city"
                    )
                    Text(
                        modifier = Modifier
                            .layoutId(CITY_TEXT_ID),
                        text = weatherState.cityWeather?.city ?: "Loading…",
                        style = MaterialTheme.typography.h4
                    )
                    Text(
                        modifier = Modifier
                            .layoutId(COUNTRY_TEXT_ID)
                            .padding(top = 8.dp),
                        text = weatherState.cityWeather?.country ?: "Loading…",
                        fontSize = 15.sp
                    )
                    Icon(
                        modifier = Modifier
                            .layoutId(REFRESH_BUTTON_ID),
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reload"
                    )
                }
            }
        }
    }
}