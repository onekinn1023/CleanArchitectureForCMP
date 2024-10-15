package com.example.filesystem.data

import com.example.core.common.DispatcherProvider
import com.example.core.common.LocalError
import com.example.core.common.Result
import com.example.core.common.SchedulePort
import com.example.filesystem.utils.FileHelper
import com.example.filesystem.utils.getUUID
import com.example.network.data.FileInfo
import kotlinx.coroutines.CoroutineDispatcher
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM

interface FileSystemRepository {
    suspend fun getFile(url: String): Result<FileInfo, LocalError>

    suspend fun zipFile(fileAbsolutePath: String): Result<Unit, LocalError>
}

class FileSystemRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val fileHelper: FileHelper
) : SchedulePort(), FileSystemRepository {

    override val scheduler: CoroutineDispatcher
        get() = dispatcherProvider.io

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