package com.devcraft.currencyassistant.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devcraft.currencyassistant.entities.Crypto
import com.devcraft.currencyassistant.entities.Post

@Database(
    entities = [Post::class, Crypto::class],
    version = 2
)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract val appDao: AppDao

    companion object {
        const val DATABASE_NAME = "crypto_database"
    }
}