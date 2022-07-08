package com.devcraft.currencyassistant.data.remote.impl

import com.devcraft.currencyassistant.BuildConfig
import com.devcraft.currencyassistant.data.remote.dto.PostResponse
import com.devcraft.currencyassistant.data.remote.network.NetworkResult
import com.devcraft.currencyassistant.data.remote.service.PostService
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
class PostServiceImpl @Inject constructor(
        private val client: HttpClient
) : PostService {

    override suspend fun getPost() : Flow<NetworkResult<PostResponse>> = flow<NetworkResult<PostResponse>>{
            val query = client.get<PostResponse> {
                url(BuildConfig.URL_MAIN_NEWS)
                parameter("auth_token", BuildConfig.API_KEY_NEWS)
                parameter("filter", BuildConfig.PARAM_NEWS)
            }
            emit(NetworkResult.Success(query))
        }.catch{
            emit(NetworkResult.ApiError(it.localizedMessage.toString()))
        }.flowOn(Dispatchers.IO)
    }

