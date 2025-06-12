package com.frommetoyou.ualachallenge.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.frommetoyou.domain.model.City
import com.frommetoyou.presentation.FilterScreen
import com.frommetoyou.presentation.MapScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable data class MapRoute(
    val city: City
)

@Serializable data object FilterRoute

fun NavGraphBuilder.mainSection(navController: NavController) {
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
            }
        )
    }
}