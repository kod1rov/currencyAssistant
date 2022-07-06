package com.devcraft.currencyassistant.data.remote

import com.devcraft.currencyassistant.data.remote.dto.CurrencyResponse
import com.devcraft.currencyassistant.data.remote.dto.PostResponse
import com.devcraft.currencyassistant.data.remote.network.HttpRoutes
import com.devcraft.currencyassistant.data.remote.network.NetworkResult
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyServiceImpl @Inject constructor(
    private val client: HttpClient
) : CurrencyService {

    override suspend fun getDataCurrency(): Flow<NetworkResult<CurrencyResponse>> =
        flow<NetworkResult<CurrencyResponse>> {
            val query = client.get<CurrencyResponse> {
                url(HttpRoutes.CURRENCY_URL)
                parameter("api-key", HttpRoutes.API_CURRENCY_KEY)
            }
            emit(NetworkResult.Success(query))
        }.catch {
            emit(NetworkResult.ApiError(it.message.toString()))
        }.flowOn(Dispatchers.IO)
}