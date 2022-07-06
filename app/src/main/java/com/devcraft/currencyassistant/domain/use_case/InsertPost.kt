package com.devcraft.currencyassistant.domain.use_case

import com.devcraft.currencyassistant.domain.repository.PostRepo
import com.devcraft.currencyassistant.entities.Post

class InsertPost(private val repo: PostRepo) {
    suspend operator fun invoke(post: List<Post>) {
        repo.insertPost(post)
    }
}