package com.frommetoyou.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.frommetoyou.core.extensions.withLoadingState
import com.frommetoyou.core.util.CoroutinesDispatcherProvider
import com.frommetoyou.core.util.Result
import com.frommetoyou.domain.model.City
import com.frommetoyou.domain.model.CityFilter
import com.frommetoyou.domain.use_case.MapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapUseCase: MapUseCase,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Nothing)
    val uiState = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow(CityFilter())
    val searchQuery = _searchQuery.asStateFlow()

    fun getCities() = viewModelScope.launch(dispatcherProvider.io) {
        mapUseCase.ensureCitiesExistIfNeeded()
            .withLoadingState()
            .collect { result ->
                when (result) {
                    is Result.Success -> {
                        _uiState.value = UiState.Success
                    }
                    is Result.Loading -> {
                        _uiState.value = UiState.Loading
                    }
                    is Result.Failure -> {
                        _uiState.value = UiState.Error
                    }
                    is Result.NotLoading -> {
                        _uiState.value = UiState.Nothing
                    }
                }
            }
    }

    fun onSearchQueryChanged(newQuery: String, onlyFavorites: Boolean) {
        _searchQuery.value = CityFilter(newQuery, onlyFavorites)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagedCities = searchQuery
        .debounce(25)
        .distinctUntilChanged()
        .flatMapLatest { filter ->
            mapUseCase.getCitiesPaged(filter)
        }
        .cachedIn(viewModelScope)


    fun deleteCities() = viewModelScope.launch(dispatcherProvider.io) {
        mapUseCase.deleteCities()

    }

    fun toggleFavorite(city: City) =
        viewModelScope.launch(dispatcherProvider.io) {
            mapUseCase.saveCity(city.copy(isFavorite = !city.isFavorite))
        }

}

sealed interface UiState {
    data object Success : UiState
    data object Error : UiState
    data object Loading : UiState
    data object Nothing : UiState
}