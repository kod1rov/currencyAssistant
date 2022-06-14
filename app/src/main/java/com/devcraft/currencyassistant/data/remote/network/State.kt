package com.devcraft.currencyassistant.data.remote.network

sealed class State<out T> {
    object Loading : State<Nothing>()
    data class Success<out T>(val data: T) : State<T>()
    data class Failed(val message: String) : State<Nothing>()

    companion object {
        fun  loading() = Loading
        fun <T> success(data: T) = Success(data)
        fun  failed(message: String) = Failed(message)
    }
}