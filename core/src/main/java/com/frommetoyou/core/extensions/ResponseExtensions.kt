package com.frommetoyou.core.extensions

import retrofit2.Response
import com.frommetoyou.core.util.Result
import okhttp3.ResponseBody

fun <T> Response<T>.parseResponse(): Result<T> {
    val responseBody = body()
    return if (responseBody != null)
        try {
            Result.Success(responseBody)
        } catch (e: Exception) {
            Result.Failure(
                errorMessage = "Response body parsing error",
                responseCode = code()
            )
        }
    else getErrorResponse(errorBody(), code())
}

fun getErrorResponse(errorBody: ResponseBody?, code: Int): Result.Failure {
    try {
        val errorMessage = errorBody?.string()
        return Result.Failure(errorMessage ?: "Retrofit unknown body error", code)
    } catch (e: Exception) {
        return Result.Failure(e.message ?: "Retrofit unknown error", code)
    }
}

fun <T> Throwable.getResponseError(): Response<T> {
    return Response.error(
        500,
        ResponseBody.create(null, message ?: "Retrofit unknown error")
    )
}