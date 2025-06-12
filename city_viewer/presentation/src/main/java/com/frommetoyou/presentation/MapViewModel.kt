package com.frommetoyou.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.frommetoyou.core.extensions.collectWithLoading
import com.frommetoyou.core.util.CoroutinesDispatcherProvider
import com.frommetoyou.core.util.Result
import com.frommetoyou.domain.model.City
import com.frommetoyou.domain.use_case.MapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapUseCase: MapUseCase,
    private val dispatcherProvider: CoroutinesDispatcherProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Nothing)
    val uiState = _uiState.asStateFlow()

    fun getCities() = viewModelScope.launch(dispatcherProvider.io) {
        mapUseCase.getCities()
            .collect { result ->
                when(result) {
                    is Result.Success -> {
                        _uiState.value = UiState.Success(result.data)
                    }
                    else -> {}
                }
            }
    }
    fun deleteCities() = viewModelScope.launch(dispatcherProvider.io) {
        mapUseCase.deleteCities()

    }


}

sealed interface UiState {
    data class Success(
        val city: List<City>
    ) : UiState
    data object Error : UiState
    data object Loading : UiState
    data object Nothing : UiState
}