package com.example.network.data

import io.ktor.client.HttpClient

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class NetworkFactory {
    fun getKtorClient(): HttpClient

    companion object {
        val SYSTEM: NetworkFactory
    }
}