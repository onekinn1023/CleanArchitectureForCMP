package fileSystem.di

import fileSystem.data.FileSystemRepository
import fileSystem.data.FileSystemRepositoryImpl
import fileSystem.domain.UploadFileUseCase
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import fileSystem.presentation.FileSystemViewModel

val fileSystemModule = module {
    single { FileSystemRepositoryImpl(get(), get(), get(), get()) }.bind<FileSystemRepository>()
    single { UploadFileUseCase(get()) }
    viewModelOf(::FileSystemViewModel)
}