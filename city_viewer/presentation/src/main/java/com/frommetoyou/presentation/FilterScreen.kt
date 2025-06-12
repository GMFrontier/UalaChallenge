package com.frommetoyou.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.frommetoyou.core_ui.composables.bouncingClickable
import com.frommetoyou.domain.model.City
import com.frommetoyou.ualachallenge.common.R

@Composable
fun FilterScreen(
    modifier: Modifier = Modifier,
    onCityClick: (City) -> Unit = {},
    onCityDetailClick: (City) -> Unit = {},
    viewModel: MapViewModel = hiltViewModel()
) {
    var filterText by remember { mutableStateOf("") }
    var showOnlyFavorites by remember { mutableStateOf(false) }

    LaunchedEffect(filterText, showOnlyFavorites) {
        viewModel.onSearchQueryChanged(filterText, showOnlyFavorites)
    }

    val lazyPagingItems = viewModel.pagedCities.collectAsLazyPagingItems()

    Column(modifier = modifier.padding(top = 16.dp)) {
        Column(modifier = modifier.padding(horizontal = 16.dp)) {
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
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    if (filterText.isNotEmpty())
                        IconButton(
                            onClick = {
                                filterText = ""
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear"
                            )
                        }
                }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Show only favorites", fontSize = 15.sp)
                Switch(
                    modifier = Modifier.height(32.dp),
                    checked = showOnlyFavorites,
                    onCheckedChange = { showOnlyFavorites = it }
                )
            }
        }
        /*Button(
            modifier = Modifier.padding(top = 12.dp),
            onClick = { viewModel.deleteCities() }
        ) {
            Text("DELETEAR CITIEES")
        }*/

        Spacer(modifier = Modifier.height(14.dp))

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(MaterialTheme.colorScheme.tertiary)
        )

        LazyColumn(modifier = modifier.padding(horizontal = 16.dp)) {
            items(count = lazyPagingItems.itemCount) { index ->
                val city = lazyPagingItems[index]
                city?.let {
                    CityItem(
                        city = it,
                        onClick = { city ->
                            onCityClick(city)
                        },
                        onCityDetailClick = onCityDetailClick,
                        onFavoriteClick = { viewModel.toggleFavorite(it) })
                }
            }

            lazyPagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            CircularProgressIndicator(
                                Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item {
                            CircularProgressIndicator(
                                Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(Alignment.CenterHorizontally)
                            )
                        }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val e =
                            lazyPagingItems.loadState.refresh as LoadState.Error
                        item { Text("Error: ${e.error.localizedMessage}") }
                    }

                    loadState.append is LoadState.Error -> {
                        val e =
                            lazyPagingItems.loadState.append as LoadState.Error
                        item { Text("Error: ${e.error.localizedMessage}") }
                    }
                }
            }
        }
    }
}

@Composable
fun CityItem(
    city: City,
    onClick: (City) -> Unit = {},
    onCityDetailClick: (City) -> Unit = {},
    onFavoriteClick: () -> Unit = {}
) {
    val favoriteIcon = if (city.isFavorite)
        painterResource(R.drawable.ic_star_filled)
    else
        painterResource(R.drawable.ic_star_outline)
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .bouncingClickable {
                onClick(city)
            }
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .weight(.8f)
            ) {
                Text(
                    text = "${city.name}, ${city.country}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Coordenadas: ${city.coordinates.lat}, ${city.coordinates.lon}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            Icon(
                Icons.Default.Info,
                contentDescription = "Favorite",
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
                    .bouncingClickable {
                        onCityDetailClick(city)
                    },
                tint = MaterialTheme.colorScheme.primary
            )
            Icon(
                favoriteIcon,
                contentDescription = "Favorite",
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
                    .bouncingClickable {
                        onFavoriteClick()
                    },
                tint = MaterialTheme.colorScheme.primary
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(1.dp)
        )
    }
}