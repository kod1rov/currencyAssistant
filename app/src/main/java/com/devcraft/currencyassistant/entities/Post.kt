package com.devcraft.currencyassistant.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Post(
    @PrimaryKey val id: Int? = null,
    val domain: String? = null,
    val title: String? = null,
    val published_at: String? = null,
    val url: String? = null
)
