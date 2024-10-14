package com.example.network.di

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
}