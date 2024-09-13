package fileSystem.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fileSystem.domain.UploadFileUseCase
import io.github.aakira.napier.Napier
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okio.FileNotFoundException

class FileSystemViewModel(
    private val uploadFileUseCase: UploadFileUseCase
) : ViewModel() {

    var uploadState by mutableStateOf(UploadState())
        private set

    private val _effectChannel = Channel<FileOperationEffect>{}
    val effect = _effectChannel.receiveAsFlow()

    private var uploadJob: Job? = null

    fun onEvent(event: FileOperationEvent) {
        when (event) {
            FileOperationEvent.CancelUpload -> cancelUploadJob()
            is FileOperationEvent.UploadFile -> uploadFile(event.contentUri)
            FileOperationEvent.SelectFile -> selectFile()
        }
    }

    private fun selectFile() {
        viewModelScope.launch {
            _effectChannel.send(FileOperationEffect.SelectFile)
        }
    }

    private fun uploadFile(contentUri: String) {
        uploadJob = uploadFileUseCase(contentUri)
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
                        errorMessage = "The upload job is cancelled!",
                        progress = 0f
                    )
                }
            }
            .catch { cause ->
                Napier.e("Failed to upload fileSystem due to $cause")
                val message = when (cause) {
                    is FileNotFoundException -> "File not found!"
                    is UnresolvedAddressException -> "No internet!"
                    else -> "Something went wrong!"
                }
                uploadState = uploadState.copy(
                    isUploading = false,
                    isUploadComplete = true,
                    errorMessage = message
                )
            }
            .launchIn(viewModelScope)
    }

    private fun cancelUploadJob() {
        Napier.d("Job canceled!")
        uploadJob?.cancel()
    }
}