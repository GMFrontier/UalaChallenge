package com.frommetoyou.core.util

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(
        val errorMessage: String,
        val responseCode: Int = 500
    ) : Result<Nothing>()

    object Loading : Result<Nothing>()
    object NotLoading : Result<Nothing>()
}