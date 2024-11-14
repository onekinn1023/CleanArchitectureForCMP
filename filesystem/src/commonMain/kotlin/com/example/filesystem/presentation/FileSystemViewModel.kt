package com.example.filesystem.presentation

import androidx.lifecycle.viewModelScope
import com.example.core.presentation.PresentationEventViewDataStore
import com.example.filesystem.domain.UploadFileUseCase
import com.example.network.data.FileInfo
import io.github.aakira.napier.Napier
import io.github.vinceglb.filekit.core.PlatformFile
import io.github.vinceglb.filekit.core.baseName
import io.github.vinceglb.filekit.core.extension
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okio.FileNotFoundException
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class FileSystemViewModel(
    private val uploadFileUseCase: UploadFileUseCase
) : PresentationEventViewDataStore<FileOperationAction, UploadState, FileOperationEvent>(
    initialState = { UploadState() }
) {

    private var uploadJob: Job? = null

    init {
        provideActionHandler()
    }

    private fun provideActionHandler() {
        viewModelScope.launch {
            actions.onStart {
                Napier.d {
                    "Start observe actions"
                }
            }.collect { action ->
                when (action) {
                    FileOperationAction.CancelUpload -> cancelUploadJob()
                    FileOperationAction.SelectFile -> selectFile()
                    is FileOperationAction.UploadFileInfo -> uploadFile(action.platformFile)
                    is FileOperationAction.UploadFile -> TODO()
                }
            }
        }
    }

    private fun selectFile() {
        viewModelScope.launch {
            send(FileOperationEvent.SelectFile)
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
                    setState {
                        it.copy(
                            isUploading = true
                        )
                    }
                }
                .onEach { progress ->
                    setState {
                        it.copy(
                            progress = progress.byteSent / progress.totalBytes.toFloat()

                        )
                    }
                }
                .onCompletion { cause ->
                    if (cause == null) {
                        setState {
                            it.copy(
                                isUploading = false,
                                isUploadComplete = true
                            )
                        }
                    } else if (cause is CancellationException) {
                        setState {
                            it.copy(
                                isUploading = false,
                                isUploadComplete = true,
                                errorMessage = "The upload job is cancelled!",
                                progress = 0f
                            )
                        }
                    }
                }
                .catch { cause ->
                    Napier.e("Failed to upload fileSystem due to $cause")
                    val message = when (cause) {
                        is FileNotFoundException -> "File not found!"
                        else -> "Something went wrong!"
                    }
                    setState {
                        it.copy(
                            isUploading = false,
                            isUploadComplete = true,
                            errorMessage = message
                        )
                    }
                }
                .launchIn(viewModelScope)
        }

    }

    private fun cancelUploadJob() {
        Napier.d("Job canceled!")
        uploadJob?.cancel()
    }
}