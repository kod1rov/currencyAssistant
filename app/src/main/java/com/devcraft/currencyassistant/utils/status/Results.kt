package com.devcraft.currencyassistant.domain.model.status

sealed class Results<T> {

    class Success<T>(val data: T, val code: Int) : Results<T>()

    class Loading<T> : Results<T>()

    class ApiError<T>(val message: String, val code: Int) : Results<T>()

    class NetworkError<T>(val throwable: Throwable) : Results<T>()

    class NullResults<T> : Results<T>()
}
