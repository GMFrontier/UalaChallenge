package com.frommetoyou.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.frommetoyou.domain.model.City

@Composable
fun FilterScreen(
    modifier: Modifier = Modifier,
    viewModel: MapViewModel = hiltViewModel()
) {
    var filterText by remember { mutableStateOf("") }
    val uiState = viewModel.uiState.collectAsState().value
    when (uiState) {
        is UiState.Success -> {
            val cities = uiState.city
            val filteredCities = remember(filterText, cities) {
                if (filterText.isEmpty()) cities
                else cities.filter {
                    it.name.contains(filterText, ignoreCase = true) ||
                            it.country.contains(filterText, ignoreCase = true)
                }
            }

            Column(modifier = modifier.padding(16.dp)) {
                OutlinedTextField(
                    value = filterText,
                    onValueChange = { filterText = it },
                    placeholder = { Text("Filter") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Icon"
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn {
                    items(filteredCities) { city ->
                        CityItem(city = city)
                    }
                }
            }
        }
        is UiState.Loading -> {
            CircularProgressIndicator(
            )
        }
        else -> {
            viewModel.getCities()
        }
    }
}

@Composable
fun CityItem(city: City) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = city.name, style = MaterialTheme.typography.titleMedium)
        Text(
            text = city.country,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}