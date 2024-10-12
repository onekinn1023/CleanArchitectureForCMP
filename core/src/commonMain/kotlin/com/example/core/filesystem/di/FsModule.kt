package com.example.core.filesystem.di

import com.example.core.common.DispatcherProvider
import com.example.core.filesystem.data.FileSystemRepository
import com.example.core.filesystem.data.FileSystemRepositoryImpl
import com.example.core.filesystem.utils.FileHelper
import io.ktor.client.HttpClient
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.example.core.filesystem.presentation")
class FileSystemViewModelModule

@Module
@ComponentScan("com.example.core.filesystem.domain")
class FileSystemDomainModule

@Module
@ComponentScan("com.example.core.filesystem.data")
class FileSystemDataModule {

    @Single
    fun provideFileHelper(): FileHelper {
        return FileHelper.SYSTEM
    }

    @Single
    fun provideFileSystemRepository(
        httpClient: HttpClient,
        dispatcherProvider: DispatcherProvider,
        fileHelper: FileHelper
    ): FileSystemRepository {
        return FileSystemRepositoryImpl(
            httpClient = httpClient,
            dispatcherProvider = dispatcherProvider,
            fileHelper = fileHelper
        )
    }
}


@Module(includes = [FileSystemDataModule::class, FileSystemDomainModule::class, FileSystemViewModelModule::class])
class FileSystemModule