package example.data.remote

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.annotation.Single
import provider.DispatcherProvider
import provider.SchedulePort
import utils.NetworkError
import utils.Result
import utils.map

interface ExampleHttpRepository {
    suspend fun getExampleText(): Result<String, NetworkError>
}

class ExampleHttpRepositoryImpl(
    private val httpClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider
): ExampleHttpRepository, SchedulePort() {

    override val scheduler: CoroutineDispatcher
        get() = dispatcherProvider.default

    override suspend fun getExampleText(): Result<String, NetworkError> {
         return scheduleCatchingNetwork<CensoredText> {
             httpClient.get(
                 urlString = "https://www.purgomalum.com/service/json"
             ) {
                 parameter("text", "test")
             }
        }.map {
            it.result
        }
    }
}