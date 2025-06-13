package com.frommetoyou.ualachallenge.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.frommetoyou.core_ui.utils.UiText
import com.frommetoyou.ualachallenge.common.R
import com.frommetoyou.ualachallenge.ui.navigation.CityDetailRoute
import com.frommetoyou.ualachallenge.ui.navigation.MapRoute
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(navController: NavController) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        rememberTopAppBarState()
    )
    val topBarState =
        rememberSaveable { (mutableStateOf(true)) }
    val title by remember {
        mutableStateOf(UiText.StringResource(R.string.back))
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route.orEmpty()

    LaunchedEffect(currentRoute) {
        val routesThatShowToolbar = listOf(
            CityDetailRoute::class.qualifiedName ?: ""
        )

        if (routesThatShowToolbar.any { currentRoute.startsWith(it) }) {
            delay(100)
            topBarState.value = true
        } else {
            topBarState.value = false
        }
    }

    if (topBarState.value)
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            title = {
                Text(
                    title.asString(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            },
            scrollBehavior = scrollBehavior,
        )
}