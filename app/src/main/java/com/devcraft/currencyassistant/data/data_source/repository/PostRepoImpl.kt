package com.devcraft.currencyassistant.data.data_source.repository

import com.devcraft.currencyassistant.data.data_source.AppDao
import com.devcraft.currencyassistant.domain.repository.PostRepo
import com.devcraft.currencyassistant.entities.Post

class PostRepoImpl(private val dao: AppDao) : PostRepo {
    override suspend fun getPost(): List<Post> = dao.getDataNews()
    override suspend fun insertPost(post: List<Post>) = dao.insertPost(post)
}