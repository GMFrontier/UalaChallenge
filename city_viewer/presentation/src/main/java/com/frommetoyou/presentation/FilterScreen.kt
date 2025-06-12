package com.frommetoyou.presentation

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.frommetoyou.domain.model.City
import com.frommetoyou.ualachallenge.common.R

@Composable
fun FilterScreen(
    modifier: Modifier = Modifier,
    onCityClick: (City) -> Unit = {},
    viewModel: MapViewModel = hiltViewModel()
) {
    var filterText by remember { mutableStateOf("") }
    var showOnlyFavorites by remember { mutableStateOf(false) }

    LaunchedEffect(filterText, showOnlyFavorites) {
        viewModel.onSearchQueryChanged(filterText, showOnlyFavorites)
    }

    val lazyPagingItems = viewModel.pagedCities.collectAsLazyPagingItems()

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

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Show only favorites")
            Spacer(modifier = Modifier.width(8.dp))
            Switch(
                checked = showOnlyFavorites,
                onCheckedChange = { showOnlyFavorites = it }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(count = lazyPagingItems.itemCount) { index ->
                val city = lazyPagingItems[index]
                city?.let {
                    CityItem(
                        city = it,
                        onClick = { city ->
                            onCityClick(city)
                        },
                        onFavoriteClick = { viewModel.toggleFavorite(it) })
                }
            }

            // Paginación y estados de carga igual que antes
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
    onFavoriteClick: () -> Unit = {}
) {
    val favoriteIcon = if (city.isFavorite)
        painterResource(R.drawable.ic_star_filled)
    else
        painterResource(R.drawable.ic_star_outlined)
    Row(
        modifier = Modifier
            .clickable {
                onClick(city)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .weight(.8f)
        ) {
            Text(text = city.name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = city.country,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
        IconButton(
            onClick = onFavoriteClick
        ) {
            Icon(
                favoriteIcon,
                contentDescription = "Favorite",
                modifier = Modifier.size(24.dp),
            )
        }
    }
}