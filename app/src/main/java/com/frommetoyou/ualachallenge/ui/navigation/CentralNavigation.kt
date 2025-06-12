package com.frommetoyou.ualachallenge.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun CentralNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = FilterRoute
    ) {
        mainSection(navController)
    }
}