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
import utils.LocalError
import utils.Result

interface FileSystemRepository {

    fun uploadFile(info: FileInfo): Flow<ProgressUpdate>

    suspend fun getFile(url: String): Result<FileInfo, LocalError>
}

class FileSystemRepositoryImpl(
    private val httpClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider.Factory,
    private val fileReader: FileReader
) : SchedulePort(), FileSystemRepository {

    override val scheduler: CoroutineDispatcher
        get() = dispatcherProvider().default

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

    override suspend fun getFile(url: String): Result<FileInfo, LocalError> =
        scheduleCatchingLocalWork {
            fileReader.uriToFileInfo(url)
        }
}