package com.frommetoyou.core.extensions

import androidx.lifecycle.MutableLiveData
import com.frommetoyou.core.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

fun <T> Flow<Result<T>>.withLoadingState(): Flow<Result<T>> =
    this.onStart { emit(Result.Loading) }
        .onCompletion { emit(Result.NotLoading) }

suspend fun <T, R : Result<T>> Flow<R>.collectWithLoading(
    loading: MutableLiveData<Boolean>,
    action: suspend (value: R) -> Unit
) = collect {
    when(it) {
        is Result.Loading -> loading.postValue(true)
        is Result.NotLoading -> loading.postValue(false)
        else -> action(it)
    }
}