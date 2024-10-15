package com.example.network.data

import com.example.core.common.DispatcherProvider
import com.example.core.common.NetworkError
import com.example.core.common.Result
import com.example.core.common.SchedulePort
import com.example.core.common.map
import com.example.network.domain.CensoredText
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow


interface FileWordRepository {
    suspend fun getCensoredText(censoredText: CensoredText): Result<CensoredText, NetworkError>

    fun uploadFile(info: FileInfo): Flow<ProgressUpdate>
}

class FileWordRepositoryImpl(
    private val httpClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider
) : FileWordRepository, SchedulePort() {

    override val scheduler: CoroutineDispatcher
        get() = dispatcherProvider.default

    private val censoredTextTypeConverter: CensoredTextTypeConverter by lazy {
        CensoredTextTypeConverter()
    }

    override suspend fun getCensoredText(censoredText: CensoredText): Result<CensoredText, NetworkError> {
        return scheduleCatchingNetwork<CensoredTextDto> {
            httpClient.get(
                urlString = "https://www.purgomalum.com/service/json"
            ) {
                parameter("text", censoredText.result)
            }
        }.map {
            censoredTextTypeConverter.convertFromDto(it)
        }
    }

    override fun uploadFile(info: FileInfo): Flow<ProgressUpdate> = channelFlow {
        httpClient.submitFormWithBinaryData(
            url = "https://dlptest.com/https-post",
            formData = formData {
                append("description", "Test")
                append(
                    "the_file",
                    info.bytes,
                    Headers.build {
                        append(HttpHeaders.ContentType, info.getMimeType().toString())
                        append(HttpHeaders.ContentDisposition, "filename=${info.name}")
                    }
                )
            }
        ) {
            onUpload { bytesSentTotal, contentLength ->
                if (contentLength > 0L) {
                    send(ProgressUpdate(bytesSentTotal, contentLength))
                }
            }
        }
    }
}