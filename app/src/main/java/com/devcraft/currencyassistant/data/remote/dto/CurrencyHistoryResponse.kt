package com.devcraft.currencyassistant.data.remote.dto


data class CurrencyHistoryResponse (
    val timestamp: Long,
    val data : List<DataHistory>
)
data class DataHistory(
    val priceUsd: Float,
    val time: Long,
    val date: String
)
