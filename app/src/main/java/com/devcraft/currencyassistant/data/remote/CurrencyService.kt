package com.devcraft.currencyassistant.data.remote

import com.devcraft.currencyassistant.data.remote.dto.CurrencyResponse
import com.devcraft.currencyassistant.data.remote.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface CurrencyService {
    suspend fun getDataCurrency(): Flow<NetworkResult<CurrencyResponse>>
}