package com.devcraft.data.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    @Named("news")
    fun provideKtorNewsHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            defaultRequest {
                url("https://cryptopanic.com/api/v1/posts/")
                parameter("auth_token", "0dca31500df91b4f3e5738fb7fc821cf729cbdb1")
                parameter( "filter", "rising")
            }
            install(JsonFeature) {
                GsonSerializer()
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    @Singleton
    @Provides
    @Named("item")
    fun provideKtorItemHttpClient(): HttpClient {
        return HttpClient(OkHttp)
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindUnsplashService(ktorUnsplashService: KtorUnsplashService): UnsplashService

    @Binds
    @Singleton
    fun bindItemRepository(itemRepositoryImpl: ItemRepositoryImpl): ItemRepository

    @Binds
    @Singleton
    fun bindUnsplashRepository(unsplashRepositoryImpl: KtorUnsplashRepositoryImpl): UnsplashRepository
}