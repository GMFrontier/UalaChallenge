package com.frommetoyou.ualachallenge.ui.screen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.frommetoyou.domain.model.City
import com.frommetoyou.presentation.FilterScreen
import com.frommetoyou.presentation.MapScreen
import com.frommetoyou.presentation.MapViewModel
import com.frommetoyou.presentation.composables.isLandscape
import com.frommetoyou.ualachallenge.ui.navigation.CityDetailRoute
import com.frommetoyou.ualachallenge.ui.navigation.FilterRoute
import com.frommetoyou.ualachallenge.ui.navigation.MapRoute

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: MapViewModel = hiltViewModel()
) {
    val landscape = isLandscape()
    val selectedCity = remember { mutableStateOf<City?>(City.getDefaultCity()) }

    var currentRoute by remember { mutableStateOf<Any>(FilterRoute) }

    if (landscape) {
        Row(modifier = Modifier.fillMaxSize()) {
            FilterScreen(
                modifier = Modifier.weight(1f),
                landscape = landscape,
                viewModel = viewModel,
                onCityClick = { city ->
                    selectedCity.value = city
                },
                onCityDetailClick = { city ->
                    navController.navigate(
                        CityDetailRoute(city)
                    )
                }
            )
            selectedCity.value?.let { city ->
                MapScreen(
                    modifier = Modifier.weight(1f),
                    city = city,
                    landscape = landscape
                )
            }
        }
    } else {
        if (currentRoute == FilterRoute)
            FilterScreen(
                modifier = Modifier.fillMaxSize(),
                viewModel = viewModel,
                onCityClick = { city ->
                    selectedCity.value = city
                    currentRoute = MapRoute
                },
                onCityDetailClick = { city ->
                    navController.navigate(
                        CityDetailRoute(city)
                    )
                }
            )
        else
            selectedCity.value?.let { city ->
                MapScreen(
                    modifier = Modifier.fillMaxSize(),
                    city = city,
                    onBackClick = {
                        currentRoute = FilterRoute
                    }
                )
            }
    }
}
