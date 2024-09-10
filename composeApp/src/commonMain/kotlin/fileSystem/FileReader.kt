package fileSystem

import io.ktor.http.ContentType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import provider.DispatcherProvider

class FileReader(
    private val dispatcherProvider: DispatcherProvider.Factory
) {

    suspend fun uriToFileInfo(contentUri: String): FileInfo {
        return withContext(dispatcherProvider().io) {
            val path = contentUri.toPath()
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
    }
}

class FileInfo(
    val name: String,
    val mimeType: String,
    val bytes: ByteArray
) {
    fun getMimeType(): ContentType {
        return when (mimeType) {
            "jpg", "jpeg" -> ContentType.Image.JPEG
            "png" -> ContentType.Image.PNG
            "gif" -> ContentType.Image.GIF
            "pdf" -> ContentType.Application.Pdf
            "txt" -> ContentType.Text.Plain
            else -> ContentType.Application.OctetStream
        }
    }
}