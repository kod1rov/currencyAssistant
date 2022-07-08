package com.devcraft.currencyassistant.data.remote.service

import com.devcraft.currencyassistant.data.remote.dto.PostResponse
import com.devcraft.currencyassistant.data.remote.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface PostService {
    suspend fun getPost(): Flow<NetworkResult<PostResponse>>
}