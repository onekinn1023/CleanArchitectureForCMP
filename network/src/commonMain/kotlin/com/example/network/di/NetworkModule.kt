package com.example.network.di

import com.example.core.common.DispatcherProvider
import com.example.network.data.FileWordRepository
import com.example.network.data.FileWordRepositoryImpl
import com.example.network.data.NetworkFactory
import io.ktor.client.HttpClient
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class NetworkModule {

    @Single
    fun provideNetworkClient(): HttpClient {
        return NetworkFactory.SYSTEM.getKtorClient()
    }

    @Single
    fun provideFileWordRepository(
        httpClient: HttpClient,
        dispatcherProvider: DispatcherProvider
    ): FileWordRepository {
        return FileWordRepositoryImpl(
            httpClient = httpClient,
            dispatcherProvider = dispatcherProvider
        )
    }
}