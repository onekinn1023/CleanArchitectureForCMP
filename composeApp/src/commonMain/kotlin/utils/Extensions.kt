package utils

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

suspend inline fun <reified D> scheduleCatchingNetwork(
    block: () -> HttpResponse
): Result<D, NetworkError> {
    return try {
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
    } catch (e: UnresolvedAddressException) {
        Result.Error(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        Result.Error(NetworkError.SERIALIZATION)
    }
}


suspend inline fun <reified D> scheduleCatchingLocalWork(
    block: () -> D
): Result<D, LocalError> {
    return try {
        Result.Success(block())
    } catch (e: Exception) {
        println("Failed to scheduleCatchingLocalWork due to $e")
        Result.Error(LocalError.UNKNOWN_ERROR)
    }
}