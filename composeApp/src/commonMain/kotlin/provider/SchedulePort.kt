package provider

import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import utils.LocalError
import utils.NetworkError
import utils.Result

abstract class SchedulePort {

    abstract val scheduler: CoroutineDispatcher

    suspend fun <D> scheduleCatchingLocalWork(
        block: suspend () -> D
    ): Result<D, LocalError> {
        return withContext(scheduler) {
            try {
                Result.Success(block())
            } catch (e: Exception) {
                coroutineContext.ensureActive()
                Napier.e(
                    tag = "SchedulePort",
                    message = "Failed to scheduleCatchingLocalWork due to $e"
                )
                Result.Error(LocalError.UNKNOWN_ERROR)
            }
        }
    }

    suspend inline fun <reified D> scheduleCatchingNetwork(
        crossinline block: suspend () -> HttpResponse,
    ): Result<D, NetworkError> {
        return withContext(scheduler) {
            try {
                val response = block()
                when (response.status.value) {
                    in 200..299 -> {
                        val data = response.body<D>()
                        Result.Success(data)
                    }
                    401 -> Result.Error(NetworkError.UNAUTHORIZED)
                    409 -> Result.Error(NetworkError.CONFLICT)
                    408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
                    413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
                    in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
                    else -> Result.Error(NetworkError.UNKNOWN)
                }
            } catch (e: Exception) {
                Napier.e(
                    tag = "SchedulePort",
                    message = "Failed to scheduleCatchingNetwork due to $e"
                )
                coroutineContext.ensureActive()
                when (e) {
                    is UnresolvedAddressException -> Result.Error(NetworkError.NO_INTERNET)
                    is SerializationException -> Result.Error(NetworkError.SERIALIZATION)
                    else -> Result.Error(NetworkError.UNKNOWN)
                }
            }
        }
    }
}