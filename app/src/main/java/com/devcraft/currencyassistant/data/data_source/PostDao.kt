package com.devcraft.currencyassistant.data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devcraft.currencyassistant.entities.Post

@Dao
interface PostDao {

    @Query("SELECT * FROM post ORDER BY id DESC")
    suspend fun getDataNews(): List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: List<Post>)
}