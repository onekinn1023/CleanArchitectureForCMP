package com.example.filesystem.presentation

import io.github.vinceglb.filekit.core.PlatformFile

data class UploadState(
    val isUploading: Boolean = false,
    val isUploadComplete: Boolean = false,
    val progress: Float = 0f,
    val errorMessage: String? = null
)

sealed interface FileOperationAction {
    data class UploadFile(val contentUri: String): FileOperationAction
    data object CancelUpload: FileOperationAction
    data object SelectFile: FileOperationAction
    data class UploadFileInfo(val platformFile: PlatformFile): FileOperationAction
}

sealed class FileOperationEvent {
    data object SelectFile: FileOperationEvent()
}