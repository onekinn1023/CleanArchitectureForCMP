package fileSystem.domain

import androidx.lifecycle.viewModelScope
import fileSystem.data.FileSystemRepository
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import okio.FileNotFoundException

class UploadFileUseCase(
    private val fileSystemRepository: FileSystemRepository
) {

    fun uploadFile(contentUri: String): Flow<ProgressUpdate>{
       return fileSystemRepository.uploadFile(contentUri)
    }

}