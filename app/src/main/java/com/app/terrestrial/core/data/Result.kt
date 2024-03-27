package com.app.terrestrial.core.data

sealed class Result<out T> {
    data class Success<out T>(val data: T) : com.app.terrestrial.core.data.Result<T>()
    data class Error(val error: String) : com.app.terrestrial.core.data.Result<Nothing>()
    data object Loading : com.app.terrestrial.core.data.Result<Nothing>()
}
