package com.example.filesystem.domain

import com.example.network.data.FileInfo
import com.example.network.data.FileWordRepository
import com.example.network.data.ProgressUpdate
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.koin.core.annotation.Single

@Single
class UploadFileUseCase(
    private val fileWordRepository: FileWordRepository
) {
    operator fun invoke(info: FileInfo): Flow<ProgressUpdate> {
        if (info.bytes.isEmpty()) {
            Napier.e(
                tag = "UploadFileUseCase",
                message = "Empty content is not allowed!"
            )
            return emptyFlow()
        }
        return fileWordRepository.uploadFile(info)
    }
}