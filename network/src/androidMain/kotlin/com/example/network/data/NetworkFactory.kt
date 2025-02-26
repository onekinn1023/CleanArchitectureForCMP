package com.example.network.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class NetworkFactory {
    actual fun getKtorClient(): HttpClient {
        return HttpClient(OkHttp.create()) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
//        install(Auth) {
//            bearer {
//                refreshTokens {
//
//                }
//            }
//        }
        }
    }

    actual companion object {
        actual val SYSTEM: NetworkFactory by lazy {
            NetworkFactory()
        }
    }
}