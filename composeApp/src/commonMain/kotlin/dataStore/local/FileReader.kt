package dataStore.local

import fileSystem.data.FileInfo
import fileSystem.getUUID
import kotlinx.coroutines.withContext
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import provider.DispatcherProvider

class FileReader(
    private val dispatcherProvider: DispatcherProvider.Factory
){

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