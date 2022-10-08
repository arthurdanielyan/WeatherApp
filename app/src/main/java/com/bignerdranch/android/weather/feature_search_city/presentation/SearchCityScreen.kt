package com.bignerdranch.android.weather.feature_search_city.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bignerdranch.android.weather.core.NO_INTERNET_MESSAGE
import com.bignerdranch.android.weather.core.log
import com.bignerdranch.android.weather.core.presentation.Screen
import com.bignerdranch.android.weather.feature_search_city.presentation.components.CityWeatherCard
import com.bignerdranch.android.weather.feature_search_city.presentation.components.MyCitiesSection


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchCityScreen(
    navController: NavController,
    viewModel: SearchCityViewModel/* = getViewModel()*/
) {
    val currentWeather = viewModel.searchedCityWeather.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    var noInternet by remember { mutableStateOf(false) }

    var searchCityTfState by rememberSaveable {
        mutableStateOf("")
    }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    if (noInternet) {
                        viewModel.searchCity(searchCityTfState)
                    }
                    noInternet = false
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    viewModel.searchCity(searchCityTfState) // this will cause the ui to tell there's no internet
                }
            })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            placeholder = { Text(text = "Search for a city") },
            value = searchCityTfState,
            onValueChange = {
                searchCityTfState = it
                viewModel.searchCity(searchCityTfState)
            },
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color(0xFF5C5C5C),
                    shape = RoundedCornerShape(5000.dp)
                )
                .clip(
                    shape = RoundedCornerShape(5000.dp)
                ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (currentWeather.value.shortWeatherInfo != null) {
            CityWeatherCard(
                modifier = Modifier.padding(vertical = 25.dp),
                weatherInfo = currentWeather.value.shortWeatherInfo!!,
                onClick = {
                    keyboardController?.hide()
                    navController.navigate(Screen.CityWeatherScreen.route + "/${currentWeather.value.shortWeatherInfo!!.city}")
                }
            )
        } else if (currentWeather.value.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 36.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Searching...",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        } else if (currentWeather.value.error.isNotEmpty() && searchCityTfState.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 36.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = currentWeather.value.error,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
            if(currentWeather.value.error == NO_INTERNET_MESSAGE)
            noInternet = true
        }
        val myCities = viewModel.myCities.collectAsState()
        log("search city screen ${myCities.value.myCities?.size}")
        if(myCities.value.myCities != null)
        MyCitiesSection(
            modifier = Modifier.fillMaxWidth(),
            myCities = myCities.value.myCities!!,
            onClick = {
                keyboardController?.hide()
                navController.navigate(Screen.CityWeatherScreen.route + "/${it}")
            }
        )
    }
}