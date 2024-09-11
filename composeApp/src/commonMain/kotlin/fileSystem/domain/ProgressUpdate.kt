package fileSystem.domain

data class ProgressUpdate(
    val byteSent: Long,
    val totalBytes: Long
)