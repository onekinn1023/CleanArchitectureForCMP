package com.example.core.filesystem.domain

import com.example.core.filesystem.data.FileInfo
import com.example.core.filesystem.data.FileSystemRepository
import com.example.core.filesystem.data.ProgressUpdate
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.koin.core.annotation.Single

@Single
class UploadFileUseCase(
    private val fileSystemRepository: FileSystemRepository
) {
    operator fun invoke(info: FileInfo): Flow<ProgressUpdate> {
        if (info.bytes.isEmpty()) {
            Napier.e(
                tag = "UploadFileUseCase",
                message = "Empty content is not allowed!"
            )
            return emptyFlow()
        }
        return fileSystemRepository.uploadFile(info)
    }
}