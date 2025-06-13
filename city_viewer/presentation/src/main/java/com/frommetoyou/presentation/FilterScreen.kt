package com.frommetoyou.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.frommetoyou.core_ui.composables.bouncingClickable
import com.frommetoyou.core_ui.utils.UiText
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
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(filterText, showOnlyFavorites) {
        viewModel.onSearchQueryChanged(filterText, showOnlyFavorites)
    }

    val lazyPagingItems = viewModel.pagedCities.collectAsLazyPagingItems()
    Scaffold(
        bottomBar = {
            Column {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    onClick = { viewModel.deleteCities() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text(
                        UiText.StringResource(R.string.clear_db,
                        ).asString())
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = { viewModel.getCities() }
                ) {
                    Text(UiText.StringResource(R.string.get_cities).asString())
                }
            }
        }
    ) { paddingValues ->
        Column(modifier = modifier.padding(top = 16.dp)) {
            Column(modifier = modifier.padding(horizontal = 16.dp)) {
                OutlinedTextField(
                    value = filterText,
                    onValueChange = { filterText = it },
                    placeholder = {
                        Text(
                            UiText.StringResource(R.string.filter).asString()
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = UiText.StringResource(R.string.icon)
                                .asString()
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
                                    contentDescription = UiText.StringResource(R.string.clear)
                                        .asString()
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
                    Text(
                        UiText.StringResource(R.string.show_favorites)
                            .asString(), fontSize = 15.sp
                    )
                    Switch(
                        modifier = Modifier.height(32.dp),
                        checked = showOnlyFavorites,
                        onCheckedChange = { showOnlyFavorites = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(MaterialTheme.colorScheme.tertiary)
            )

            when (uiState) {
                is UiState.Error -> {
                    Text(
                        modifier = Modifier.padding(32.dp),
                        text = UiText.StringResource(R.string.error).asString(),
                        color = Color.Red
                    )
                }

                is UiState.Loading -> {
                    CircularProgressIndicator(
                        Modifier
                            .fillMaxWidth()
                            .padding(32.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }

                is UiState.Success, UiState.Nothing -> {
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
                                    onFavoriteClick = {
                                        viewModel.toggleFavorite(
                                            it
                                        )
                                    })
                            }
                        }

                        lazyPagingItems.apply {
                            when {
                                loadState.refresh is LoadState.Loading -> {
                                    item {
                                        CircularProgressIndicator(
                                            Modifier
                                                .fillMaxWidth()
                                                .padding(24.dp)
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

                                loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                                    val e =
                                        lazyPagingItems.loadState.refresh as LoadState.Error
                                    item {
                                        Text(
                                            UiText.StringResource(
                                                R.string.error,
                                                arrayOf(
                                                    e.error.localizedMessage
                                                        ?: ""
                                                )
                                            ).asString()
                                        )
                                    }
                                }
                            }
                        }
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