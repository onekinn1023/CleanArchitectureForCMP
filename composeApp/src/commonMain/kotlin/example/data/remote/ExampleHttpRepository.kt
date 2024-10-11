package example.data.remote

import com.example.core.common.NetworkError
import com.example.core.common.Result
import com.example.core.common.SchedulePort
import com.example.core.common.map
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.CoroutineDispatcher
import provider.DispatcherProvider

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