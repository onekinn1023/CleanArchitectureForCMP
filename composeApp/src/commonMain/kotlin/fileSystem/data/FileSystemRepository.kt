package fileSystem.data

import dataStore.local.FileReader
import fileSystem.domain.ProgressUpdate
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import provider.DispatcherProvider
import provider.SchedulePort

interface FileSystemRepository {

    fun uploadFile(contentUri: String): Flow<ProgressUpdate>
}

class FileSystemRepositoryImpl(
    private val httpClient: HttpClient,
    private val fileReader: FileReader,
    private val dispatcherProvider: DispatcherProvider.Factory
): SchedulePort(), FileSystemRepository {

    override val scheduler: CoroutineDispatcher
        get() = dispatcherProvider().default

    override fun uploadFile(contentUri: String): Flow<ProgressUpdate> = channelFlow {
        val info = fileReader.uriToFileInfo(contentUri)
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