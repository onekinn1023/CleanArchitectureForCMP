package dataStore.remote

import provider.DispatcherProvider
import provider.SchedulePort
import data.remote.CensoredText
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.CoroutineDispatcher
import utils.NetworkError
import utils.Result
import utils.map

class ExampleHttpDataSource(
    private val httpClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider.Factory
): SchedulePort() {

    override val scheduler: CoroutineDispatcher
        get() = dispatcherProvider().default

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