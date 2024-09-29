package fileSystem.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fileSystem.data.FileInfo
import fileSystem.domain.UploadFileUseCase
import io.github.aakira.napier.Napier
import io.github.vinceglb.filekit.core.PlatformFile
import io.github.vinceglb.filekit.core.baseName
import io.github.vinceglb.filekit.core.extension
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
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
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
            FileOperationEvent.SelectFile -> selectFile()
            is FileOperationEvent.UploadFileInfo -> uploadFile(event.platformFile)
            is FileOperationEvent.UploadFile -> TODO()
        }
    }

    private fun selectFile() {
        viewModelScope.launch {
            _effectChannel.send(FileOperationEffect.SelectFile)
        }
    }

    private fun uploadFile(platformFile: PlatformFile) {
        viewModelScope.launch {
            val info = FileInfo(
                name = platformFile.baseName,
                mimeType = platformFile.extension,
                bytes = platformFile.readBytes()
            )
            Napier.d("Get file with $info")
            uploadJob = uploadFileUseCase(info)
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

    }

    private fun cancelUploadJob() {
        Napier.d("Job canceled!")
        uploadJob?.cancel()
    }
}