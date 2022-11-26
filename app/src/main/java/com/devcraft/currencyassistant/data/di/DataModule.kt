package com.devcraft.currencyassistant.data.di

import android.app.Application
import androidx.room.Room
import com.devcraft.currencyassistant.data.data_source.CurrencyDatabase
import com.devcraft.currencyassistant.data.data_source.repository.CryptoRepoImpl
import com.devcraft.currencyassistant.data.data_source.repository.PostRepoImpl
import com.devcraft.currencyassistant.domain.repository.CryptoRepo
import com.devcraft.currencyassistant.domain.repository.PostRepo
import com.devcraft.currencyassistant.domain.use_case.*
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
    fun provideCryptoDatabase(app: Application): CurrencyDatabase =
        Room.databaseBuilder(app, CurrencyDatabase::class.java, CurrencyDatabase.DATABASE_NAME)
            .build()

    @Provides
    @Singleton
    fun providePostRepository(db: CurrencyDatabase): PostRepo = PostRepoImpl(db.appDao)

    @Provides
    @Singleton
    fun provideNewsUseCases(repository: PostRepo): PostUseCase = PostUseCase(
        getPost = GetPost(repository),
        insertPost = InsertPost(repository)
    )

    @Provides
    @Singleton
    fun provideCryptoRepository(db: CurrencyDatabase): CryptoRepo = CryptoRepoImpl(db.appDao)

    @Provides
    @Singleton
    fun provideCryptoUseCases(repository: CryptoRepo): CryptoUseCase = CryptoUseCase(
        getCryptoData = GetCrypto(repository),
        insertCrypto = InsertCrypto(repository)
    )

}

