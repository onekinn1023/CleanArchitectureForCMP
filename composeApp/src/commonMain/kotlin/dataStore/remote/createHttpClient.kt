package dataStore.remote

import io.ktor.client.HttpClient

expect fun createHttpClient(): HttpClient
