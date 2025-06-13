package com.frommetoyou.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.frommetoyou.core_ui.utils.UiText
import com.frommetoyou.domain.model.City
import com.frommetoyou.presentation.composables.GoogleMaps
import com.frommetoyou.ualachallenge.common.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    city: City,
    landscape: Boolean = false,
    onBackClick: () -> Unit = {},
) {
    val cameraPositionState = rememberCameraPositionState {

        position = CameraPosition.fromLatLngZoom(
            LatLng(city.coordinates.lat, city.coordinates.lon), 14f
        )
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(city) {
        scope.launch {
            val delta = 0.05

            val bounds = LatLngBounds(
                LatLng(
                    city.coordinates.lat - delta,
                    city.coordinates.lon - delta
                ),
                LatLng(
                    city.coordinates.lat + delta,
                    city.coordinates.lon + delta
                )
            )
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngBounds(bounds, 64),
                durationMs = 2000
            )
        }
    }
    Column(
        modifier = modifier,
    ) {
        if (landscape.not()) CenterAlignedTopAppBar(
            windowInsets = WindowInsets(
                top = 0.dp,
                bottom = 0.dp
            ),
            title = {
                Text(
                    UiText.StringResource(R.string.back).asString(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            },
        )
        GoogleMaps(
            initialCameraPosition = cameraPositionState,
            markers = listOf(LatLng(city.coordinates.lat, city.coordinates.lon))
        )
    }
}