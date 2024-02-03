package com.hoopCarpool.movies.data.mapper

import com.hoopCarpool.movies.domain.model.ApiError
import com.hoopCarpool.movies.domain.model.NetworkError
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toNetworkError():NetworkError{
    val error = when(this){
        is IOException -> ApiError.NetworkError
        is HttpException -> ApiError.UnknownResponse
        else -> ApiError.UnknownResponse
    }
    return NetworkError(
        error = error,
        t = this
    )
}