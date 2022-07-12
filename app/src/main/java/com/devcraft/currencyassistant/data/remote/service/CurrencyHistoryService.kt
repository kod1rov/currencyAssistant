package com.devcraft.currencyassistant.data.remote.service

import com.devcraft.currencyassistant.data.remote.dto.CurrencyHistoryResponse
import com.devcraft.currencyassistant.data.remote.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface CurrencyHistoryService {
    suspend fun getCurrencyHistory(id: String, filter:Int) : Flow<NetworkResult<CurrencyHistoryResponse>>
}