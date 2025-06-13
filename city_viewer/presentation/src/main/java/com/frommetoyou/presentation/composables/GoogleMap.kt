package com.frommetoyou.presentation.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberUpdatedMarkerState


@Composable
fun GoogleMaps(
    modifier: Modifier = Modifier,
    initialCameraPosition: CameraPositionState,
    markers: List<LatLng> = emptyList()
) {
    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = initialCameraPosition,
        properties = MapProperties(isMyLocationEnabled = false)
    ) {
        markers.forEach {
            Marker(
                title = "Marker Title",
                snippet = "Marker Snippet",
                state = rememberUpdatedMarkerState(position = it)
            )
        }
    }
}