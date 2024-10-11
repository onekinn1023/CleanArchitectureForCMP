package fileSystem.domain

import fileSystem.data.FileInfo
import fileSystem.data.FileSystemRepository
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.koin.core.annotation.Single

@Single
class UploadFileUseCase(
    private val fileSystemRepository: FileSystemRepository
) {

    operator fun invoke(info: FileInfo): Flow<ProgressUpdate> {
        if (info.bytes.isEmpty()) {
            Napier.e(
                tag = "UploadFileUseCase",
                message = "Empty content is not allowed!"
            )
            return emptyFlow()
        }
        return fileSystemRepository.uploadFile(info)
    }
}