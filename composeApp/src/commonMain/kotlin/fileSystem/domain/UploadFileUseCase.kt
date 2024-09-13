package fileSystem.domain

import androidx.lifecycle.viewModelScope
import fileSystem.data.FileSystemRepository
import io.github.aakira.napier.Napier
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import okio.FileNotFoundException

class UploadFileUseCase(
    private val fileSystemRepository: FileSystemRepository
) {

    operator fun invoke(contentUri: String): Flow<ProgressUpdate> {
        if (contentUri.isBlank()) {
            Napier.e(
                tag ="UploadFileUseCase",
                message = "Empty content is not allowed!"
            )
            return emptyFlow()
        }
        return fileSystemRepository.uploadFile(contentUri)
    }
}