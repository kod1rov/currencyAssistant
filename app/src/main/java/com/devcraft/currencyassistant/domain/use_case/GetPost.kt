package com.devcraft.currencyassistant.domain.use_case

import com.devcraft.currencyassistant.domain.repository.PostRepo
import com.devcraft.currencyassistant.entities.Post

class GetPost(private val repo: PostRepo) {
    suspend operator fun invoke(): List<Post> = repo.getPost()
}