package fileSystem

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import okio.FileNotFoundException

class FileSystemViewModel(
    private val fileRepository: FileRepository
) : ViewModel() {

    var uploadState by mutableStateOf(UploadState())
        private set

    private var uploadJob: Job? = null

    fun uploadFile(contentUri: String) {
        uploadJob = fileRepository
            .uploadFile(contentUri)
            .onStart {
                uploadState = uploadState.copy(
                    isUploading = true
                )
            }
            .onEach {
                uploadState = uploadState.copy(
                    progress = it.byteSent / it.totalBytes.toFloat()
                )
            }
            .onCompletion { cause ->
                if (cause == null) {
                    uploadState = uploadState.copy(
                        isUploading = false,
                        isUploadComplete = true
                    )
                } else if (cause is CancellationException) {
                    uploadState = uploadState.copy(
                        isUploading = false,
                        isUploadComplete = true,
                        errorMessage = "The upload job is cancelled!"
                    )
                }
            }
            .catch { cause ->
                val message = when(cause) {
                    is FileNotFoundException -> "File not found!"
                    is UnresolvedAddressException -> "No internet!"
                    else -> "Something went wrong"
                }

            }
            .launchIn(viewModelScope)
    }
}

data class UploadState(
    val isUploading: Boolean = false,
    val isUploadComplete: Boolean = false,
    val progress: Float = 0f,
    val errorMessage: String? = null
)