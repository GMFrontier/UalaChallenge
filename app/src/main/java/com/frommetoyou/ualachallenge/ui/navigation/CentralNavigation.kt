package com.frommetoyou.ualachallenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.frommetoyou.core_ui.utils.UiText
import kotlinx.serialization.Serializable

@Composable
fun CentralNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.MapScreen.route
    ) {
        mainSection(navController)
    }
}

@Composable
fun getScreens(): List<Screens<out Any>> {
    return listOf(
        Screens.MapScreen,
        Screens.FilterScreen
    )
}

@Serializable
sealed class Screens<T>(
    val name: UiText, // the name of the tab
    val route: String

) {

    @Serializable
    data object MapScreen : Screens<MapRoute>(
        name = UiText.DynamicString("Map Screen"),
        route = MapRoute.route
    )

    @Serializable
    data object FilterScreen : Screens<FilterRoute>(
        name = UiText.DynamicString("Filter Screen"),
        route = FilterRoute.route
    )
}