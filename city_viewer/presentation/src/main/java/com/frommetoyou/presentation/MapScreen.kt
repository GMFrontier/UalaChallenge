package com.frommetoyou.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.frommetoyou.domain.model.City
import com.frommetoyou.presentation.composables.GoogleMaps
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    city: City,
) {
    val cameraPositionState = rememberCameraPositionState {

        position = CameraPosition.fromLatLngZoom(
            LatLng(city.coordinates.lat, city.coordinates.lon), 14f
        )
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            val delta = 0.05

            val bounds = LatLngBounds(
                LatLng(city.coordinates.lat - delta, city.coordinates.lon - delta),
                LatLng(city.coordinates.lat + delta, city.coordinates.lon + delta)
            )
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngBounds(bounds, 64),
                durationMs = 2000
            )
        }
    }
    GoogleMaps(
        initialCameraPosition = cameraPositionState,
        markers = listOf(LatLng(city.coordinates.lat, city.coordinates.lon))
    )
}