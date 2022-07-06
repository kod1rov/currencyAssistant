package com.devcraft.currencyassistant.data.di

import android.app.Application
import androidx.room.Room
import com.devcraft.currencyassistant.data.data_source.PostDatabase
import com.devcraft.currencyassistant.data.data_source.repository.PostRepoImpl
import com.devcraft.currencyassistant.domain.repository.PostRepo
import com.devcraft.currencyassistant.domain.use_case.GetPost
import com.devcraft.currencyassistant.domain.use_case.InsertPost
import com.devcraft.currencyassistant.domain.use_case.PostUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideKtorNewsHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            expectSuccess = true
            install(JsonFeature) {
                serializer
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    @Provides
    @Singleton
    fun providePostDatabase(app: Application): PostDatabase = Room.databaseBuilder(
        app,
        PostDatabase::class.java,
        PostDatabase.DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun providePostRepository(db: PostDatabase): PostRepo = PostRepoImpl(db.tradingDao)

    @Provides
    @Singleton
    fun provideTradingUseCases(repository: PostRepo): PostUseCase = PostUseCase(
        getPost = GetPost(repository),
        insertPost = InsertPost(repository)
    )
}

