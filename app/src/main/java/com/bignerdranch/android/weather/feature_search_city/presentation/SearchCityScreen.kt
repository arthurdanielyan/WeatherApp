package com.bignerdranch.android.weather.feature_search_city.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
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
import com.bignerdranch.android.weather.core.presentation.Screen
import com.bignerdranch.android.weather.feature_search_city.presentation.components.CityWeatherCard
import kotlinx.coroutines.launch


@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchCityScreen(
    navController: NavController,
    viewModel: SearchCityViewModel
) {
    var navigationAllowed by remember { mutableStateOf(true) }
    val currentWeather = viewModel.searchedCityWeather.collectAsState()
    val myCities = viewModel.myCities.collectAsState().value
    val keyboardController = LocalSoftwareKeyboardController.current
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var noInternet by remember { mutableStateOf(false) }

    var searchCityTfState by rememberSaveable {
        mutableStateOf("")
    }

//    val visibleState = remember {
//        MutableTransitionState(false).apply {
//            targetState = true
//        }
//    }
//    var itemCount by remember { mutableStateOf(0) }
//    var animPlayed by rememberSaveable { mutableStateOf(false) }
//
//    LaunchedEffect(Unit) {
//        itemCount = myCities.value.myCities?.size ?: 0
//    }
//
//    val itemCountAnim by animateIntAsState(
//        targetValue = itemCount,
//        animationSpec = tween(1000),
//        finishedListener = {
//            animPlayed = true
//        }
//    )

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
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                placeholder = { Text(text = "Search a city") },
                value = searchCityTfState,
                onValueChange = {
                    searchCityTfState = it
                    viewModel.searchCity(searchCityTfState)
                    coroutineScope.launch {
                        lazyListState.scrollToItem(0)
                    }
                },
                modifier = Modifier
                    .weight(1f)
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
            var expanded by remember { mutableStateOf(false) }
            Box {
                IconButton(
                    modifier = Modifier
                        .padding(start = 6.dp),
                    onClick = {
                        expanded = true
                    }
                ) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Dropdown menu")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    Divider()
                    DropdownMenuItem(onClick = {
                        if (navigationAllowed) {
                            navController.navigate(Screen.SettingsScreen.route)
                            expanded = false
                        }
                        navigationAllowed = false
                    }) {
                        Text("Configure Units")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))


        LazyColumn(
            state = lazyListState
        ) {
            item {
                if (currentWeather.value.searchedCities.isNotEmpty() && !currentWeather.value.isLoading)
                    Column {
                        (currentWeather.value.searchedCities).forEach { cityWeather ->
                            CityWeatherCard(
                                modifier = Modifier.padding(vertical = 8.dp),
                                weatherInfo = cityWeather,
                                onClick = {
                                    if (navigationAllowed) {
                                        keyboardController?.hide()
                                        navController.navigate(Screen.CityWeatherScreen.route + "/${cityWeather.city}") {
                                            popUpTo(Screen.SearchCityScreen.route)
                                        }
                                        navigationAllowed = false
                                    }
                                }
                            )
                        }
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
                } else if (currentWeather.value.searchedCities.isEmpty() && !currentWeather.value.isLoading && searchCityTfState.isNotBlank()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 36.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Nothing found",
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            item {
                Text(text = "My Cities")
                Spacer(modifier = Modifier.height(8.dp))
                Divider(modifier = Modifier.fillMaxWidth())
            }
            if (!myCities.isLoading && myCities.myCities.isNotEmpty()) {
                items(myCities.myCities.size) {index ->
                    CityWeatherCard(
                        modifier = Modifier.padding(vertical = 12.dp),
                        weatherInfo = myCities.myCities[index],
                        onClick = {
                            if (navigationAllowed) {
                                keyboardController?.hide()
                                navController.navigate(Screen.CityWeatherScreen.route + "/${it}") {
                                    popUpTo(Screen.SearchCityScreen.route)
                                }
                                navigationAllowed = false
                            }
                        }
                    )

                }
            }
        }
    }
}