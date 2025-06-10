package com.frommetoyou.ualachallenge.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.frommetoyou.presentation.FilterScreen
import com.frommetoyou.presentation.MapScreen
import kotlinx.serialization.Serializable

@Serializable data object MapRoute {
    const val route = "map_screen"
}

@Serializable data object FilterRoute {
    const val route = "filter_screen"
}

fun NavGraphBuilder.mainSection(navController: NavController) {
    composable(MapRoute.route) { MapScreen() }
    composable(FilterRoute.route) { FilterScreen() }
}