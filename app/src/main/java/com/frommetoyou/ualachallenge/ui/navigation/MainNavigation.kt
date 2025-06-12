package com.frommetoyou.ualachallenge.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.frommetoyou.domain.model.City
import com.frommetoyou.presentation.FilterScreen
import kotlinx.serialization.Serializable

@Serializable data class MapRoute(
    val city: City
)

@Serializable data object FilterRoute

fun NavGraphBuilder.mainSection(navController: NavController) {
    composable<MapRoute> {
        val arguments = it.toRoute<MapRoute>()
    }
    composable<FilterRoute> { FilterScreen() }
}