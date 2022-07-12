package com.devcraft.currencyassistant.data.remote.impl

import com.devcraft.currencyassistant.BuildConfig
import com.devcraft.currencyassistant.data.remote.dto.CurrencyHistoryResponse
import com.devcraft.currencyassistant.data.remote.network.NetworkResult
import com.devcraft.currencyassistant.data.remote.service.CurrencyHistoryService
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyHistoryImpl @Inject constructor(
    private val client: HttpClient
) : CurrencyHistoryService {

    override suspend fun getCurrencyHistory(id: String, filter: Int): Flow<NetworkResult<CurrencyHistoryResponse>> =
        flow<NetworkResult<CurrencyHistoryResponse>> {
            val cEndDate = Calendar.getInstance()
            val cStartDate = Calendar.getInstance()
            cStartDate.add(Calendar.DAY_OF_YEAR, filter)
            val query = client.get<CurrencyHistoryResponse> {
                url{
                    url(BuildConfig.URL_MAIN_CURRENCY)
                    pathComponents(listOf(id, "history"))
                    parameters.append("api-key", BuildConfig.API_KEY_CURRENCY)
                    parameters.append("interval", "d1")
                    parameters.append("start", cStartDate.timeInMillis.toString())
                    parameters.append("end", cEndDate.timeInMillis.toString())
                }
            }
            emit(NetworkResult.Success(query))
        }.catch {
            emit(NetworkResult.ApiError(it.message.toString()))
        }.flowOn(Dispatchers.IO)
}
