package com.frommetoyou.presentation

import androidx.lifecycle.ViewModel
import com.frommetoyou.domain.use_case.MapUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val mapUseCase: MapUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Nothing)
    val uiState = _uiState.asStateFlow()

    init {
        mapUseCase.getCountries()
    }


}

sealed interface UiState {
    data class Success(val string: String) : UiState
    data object Error : UiState
    data object Loading : UiState
    data object Nothing : UiState
}