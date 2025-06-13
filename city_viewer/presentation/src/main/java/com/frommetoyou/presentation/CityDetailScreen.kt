package com.frommetoyou.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.frommetoyou.core_ui.utils.UiText
import com.frommetoyou.domain.model.City
import com.frommetoyou.ualachallenge.common.R

@Composable
fun CityDetailScreen(
    city: City,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        CityDetailCard(city = city)
    }
}

@Composable
fun CityDetailCard(city: City) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "${city.name}, ${city.country}",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = UiText.StringResource(R.string.city_id, arrayOf(city.id)).asString(),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = UiText.StringResource(R.string.city_lat, arrayOf(city.coordinates.lat)).asString(),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text =  UiText.StringResource(R.string.city_lon, arrayOf(city.coordinates.lon)).asString(),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = UiText.StringResource(R.string.favorite).asString(),
                    style = MaterialTheme.typography.bodyLarge
                )
                Icon(
                    painter = if (city.isFavorite)
                        painterResource(id = R.drawable.ic_star_filled)
                    else
                        painterResource(id = R.drawable.ic_star_outline),
                    contentDescription = UiText.StringResource(R.string.favorite).asString(),
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(24.dp)
                )
            }
        }
    }
}