package fileSystem.presentation

data class UploadState(
    val isUploading: Boolean = false,
    val isUploadComplete: Boolean = false,
    val progress: Float = 0f,
    val errorMessage: String? = null
)

sealed interface FileOperationEvent {
    data class UploadFile(val contentUri: String): FileOperationEvent
    data object CancelUpload: FileOperationEvent
    data object SelectFile: FileOperationEvent
}

sealed class FileOperationEffect {
    data object SelectFile: FileOperationEffect()
}