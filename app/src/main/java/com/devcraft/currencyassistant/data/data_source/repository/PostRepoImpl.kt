package com.devcraft.currencyassistant.data.data_source.repository

import com.devcraft.currencyassistant.data.data_source.PostDao
import com.devcraft.currencyassistant.data.remote.dto.Results
import com.devcraft.currencyassistant.domain.repository.PostRepo
import com.devcraft.currencyassistant.entities.Post

class PostRepoImpl(
    private val dao: PostDao
) : PostRepo {
    override suspend fun getPost(): List<Post> = dao.getDataNews()
    override suspend fun insertPost(post: List<Post>) {
        dao.insertPost(post)
    }

}