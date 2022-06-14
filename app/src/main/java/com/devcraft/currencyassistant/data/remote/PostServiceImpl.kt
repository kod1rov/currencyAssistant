package com.devcraft.currencyassistant.data.remote

import com.devcraft.currencyassistant.data.remote.dto.PostResponse
import com.devcraft.currencyassistant.data.remote.network.NetworkResult
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class PostServiceImpl @Inject constructor(
    @Named("news") private val client: HttpClient
) : PostService {

    override suspend fun getPost() : Flow<NetworkResult<PostResponse>> = flow<NetworkResult<PostResponse>>{
            val query = client.get<PostResponse> {
                url(HttpRoutes.URL)
                parameter("auth_token", HttpRoutes.API_KEY)
                parameter("filter", HttpRoutes.PARAM)
            }
            emit(NetworkResult.Success(query))
        }.catch{
            emit(NetworkResult.ApiError(it.localizedMessage.toString()))
        }.flowOn(Dispatchers.IO)
    }

