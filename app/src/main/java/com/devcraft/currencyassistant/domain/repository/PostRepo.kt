package com.devcraft.currencyassistant.domain.repository

import com.devcraft.currencyassistant.entities.Post

interface PostRepo {
    suspend fun getPost() : List<Post>
    suspend fun insertPost(post: List<Post>)
}