package com.frommetoyou.core.extensions

import com.frommetoyou.core.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

fun <T> Flow<Result<T>>.withLoadingState(): Flow<Result<T>> =
    this.onStart { emit(Result.Loading) }
        .onCompletion { emit(Result.NotLoading) }

suspend fun <T, R : Result<T>> Flow<R>.collectWithLoading(
    loading: MutableStateFlow<Boolean>,
    action: suspend (value: R) -> Unit
) = collect {
    when(it) {
        is Result.Loading -> loading.value = (true)
        is Result.NotLoading -> loading.value = (false)
        else -> action(it)
    }
}