package com.frommetoyou.ualachallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.frommetoyou.core_ui.composables.MyToolbar
import com.frommetoyou.ualachallenge.ui.navigation.CentralNavigation
import com.frommetoyou.ualachallenge.ui.navigation.FilterRoute
import com.frommetoyou.ualachallenge.ui.navigation.MapRoute
import com.frommetoyou.ualachallenge.ui.theme.UalaChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UalaChallengeTheme {
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        val currentDestination = navController.currentBackStackEntryAsState().value
                        when (currentDestination?.destination?.route) {
                            MapRoute.route -> {
                                MyToolbar(
                                    title = "Back",
                                    onBackClick = {
                                        navController.navigate(FilterRoute.route)
                                    }
                                )
                            }
                            FilterRoute.route -> {
                                // Podés mostrar otra toolbar o ninguna
                            }
                            else -> {
                                // No mostrar toolbar en otras pantallas
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        CentralNavigation(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UalaChallengeTheme {
        Greeting("Android")
    }
}