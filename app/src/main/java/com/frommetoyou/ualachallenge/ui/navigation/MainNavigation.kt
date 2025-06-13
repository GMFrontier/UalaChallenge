package com.frommetoyou.ualachallenge.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.frommetoyou.domain.model.City
import com.frommetoyou.presentation.CityDetailScreen
import com.frommetoyou.presentation.FilterScreen
import com.frommetoyou.presentation.MapScreen
import com.frommetoyou.ualachallenge.ui.screen.HomeScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable data class MapRoute(
    val city: City
)

@Serializable data class CityDetailRoute(
    val city: City
)

@Serializable data object FilterRoute

@Serializable data object HomeRoute

fun NavGraphBuilder.mainSection(navController: NavController) {
    composable<HomeRoute> {
        HomeScreen(navController)
    }
    composable<CityDetailRoute>(
        typeMap = mapOf(
            typeOf<City>() to CustomNavType.CityType,
        )
    ) {
        val arguments = it.toRoute<MapRoute>()
        CityDetailScreen(
            city = arguments.city,
        )
    }
    composable<MapRoute>(
        typeMap = mapOf(
            typeOf<City>() to CustomNavType.CityType,
        )
    ) {
        val arguments = it.toRoute<MapRoute>()
        MapScreen(
            city = arguments.city,
        )
    }
    composable<FilterRoute> {
        FilterScreen(
            onCityClick = { city ->
                navController.navigate(
                    MapRoute(city)
                )
            },
            onCityDetailClick = { city ->
                navController.navigate(
                    CityDetailRoute(city)
                )
            }
        )
    }
}