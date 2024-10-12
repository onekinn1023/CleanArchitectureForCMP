package com.example.core.network.di

import com.example.core.network.NetworkFactory
import io.ktor.client.HttpClient
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class NetworkModule {

    @Single
    fun provideNetworkClient(): HttpClient {
        return NetworkFactory.SYSTEM.getKtorClient()
    }
}