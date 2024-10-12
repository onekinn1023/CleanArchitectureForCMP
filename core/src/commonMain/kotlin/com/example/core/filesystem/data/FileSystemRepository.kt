package com.example.core.filesystem.data

import com.example.core.common.DispatcherProvider
import com.example.core.common.LocalError
import com.example.core.common.Result
import com.example.core.common.SchedulePort
import com.example.core.filesystem.utils.FileHelper
import com.example.core.filesystem.utils.getUUID
import io.ktor.client.HttpClient
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM

interface FileSystemRepository {
    fun uploadFile(info: FileInfo): Flow<ProgressUpdate>

    suspend fun getFile(url: String): Result<FileInfo, LocalError>

    suspend fun zipFile(fileAbsolutePath: String): Result<Unit, LocalError>
}

class FileSystemRepositoryImpl(
    private val httpClient: HttpClient,
    private val dispatcherProvider: DispatcherProvider,
    private val fileHelper: FileHelper
) : SchedulePort(), FileSystemRepository {

    override val scheduler: CoroutineDispatcher
        get() = dispatcherProvider.io

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
            val path = url.toPath()
            val bytes = FileSystem.SYSTEM.read(path) {
                readByteArray()
            }
            val name = getUUID()
            val mimeType = path.name.substringAfterLast('.', "").lowercase()
            FileInfo(
                name = name,
                mimeType = mimeType,
                bytes = bytes
            )
        }

    override suspend fun zipFile(fileAbsolutePath: String): Result<Unit, LocalError> {
        return scheduleCatchingLocalWork {
            val path = fileAbsolutePath.toPath()
            val outputZip = path.parent.toString() + "/${path.name}.zip"
            fileHelper.compressFile(outputZip, fileAbsolutePath)
        }
    }
}