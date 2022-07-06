package com.devcraft.currencyassistant.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devcraft.currencyassistant.entities.Post

@Database(
    entities = [Post::class],
    version = 1
)
abstract class PostDatabase : RoomDatabase() {
    abstract val tradingDao: PostDao

    companion object {
        const val DATABASE_NAME = "Post_database"
    }
}