package com.devcraft.currencyassistant.data.remote.network

sealed class NetworkResult<out T> {

    class Success<T>(val data: T) : NetworkResult<T>()

    class Loading : NetworkResult<Nothing>()

    class ApiError(val message: String) : NetworkResult<Nothing>()

    class NetworkError(val throwable: Throwable) : NetworkResult<Nothing>()

    class NullNetworkResult<T> : NetworkResult<T>()
}
