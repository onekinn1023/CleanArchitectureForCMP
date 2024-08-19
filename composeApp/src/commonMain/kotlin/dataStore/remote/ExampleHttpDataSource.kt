package dataStore.remote

import data.remote.CensoredText
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import utils.NetworkError
import utils.Result
import utils.map
import utils.scheduleCatchingNetwork

class ExampleHttpDataSource(
    private val httpClient: HttpClient
) {

    suspend fun censorWords(uncensored: String) : Result<String, NetworkError> {
        return scheduleCatchingNetwork<CensoredText> {
            httpClient.get(
                urlString = "https://www.purgomalum.com/service/json"
            ) {
                parameter("text", uncensored)
            }
        }.map {
            it.result
        }
    }
}