package fileSystem

import io.ktor.client.HttpClient
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.append
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.ifNoneMatch
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import provider.DispatcherProvider

class FileRepository(
    private val httpClient: HttpClient,
    private val fileReader: FileReader,
) {
    fun uploadFile(contentUri: String): Flow<ProgressUpdate> = channelFlow {
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

data class ProgressUpdate(
    val byteSent: Long,
    val totalBytes: Long
)