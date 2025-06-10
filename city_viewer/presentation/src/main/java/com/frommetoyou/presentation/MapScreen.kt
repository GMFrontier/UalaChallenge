package com.frommetoyou.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.frommetoyou.presentation.composables.GoogleMaps
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch


@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    val cameraPositionState = rememberCameraPositionState {

        position = CameraPosition.fromLatLngZoom(
            LatLng(-34.596932, -58.435901), 14f
        )
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            val bounds = LatLngBounds(
                LatLng(-34.636268, -58.530017),
                LatLng(-34.526186, -58.373091)
            )
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngBounds(bounds, 64),
                durationMs = 2000
            )
        }
    }
    GoogleMaps(
        initialCameraPosition = cameraPositionState,
    )
}