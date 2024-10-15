package com.example.filesystem.di

import com.example.core.common.DispatcherProvider
import com.example.filesystem.data.FileSystemRepository
import com.example.filesystem.data.FileSystemRepositoryImpl
import com.example.filesystem.utils.FileHelper
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
@ComponentScan("com.example.filesystem.presentation")
class FileSystemViewModelModule

@Module
@ComponentScan("com.example.filesystem.domain")
class FileSystemDomainModule

@Module
@ComponentScan("com.example.filesystem.data")
class FileSystemDataModule {

    @Single
    fun provideFileHelper(): FileHelper {
        return FileHelper.SYSTEM
    }

    @Single
    fun provideFileSystemRepository(
        dispatcherProvider: DispatcherProvider,
        fileHelper: FileHelper
    ): FileSystemRepository {
        return FileSystemRepositoryImpl(
            dispatcherProvider = dispatcherProvider,
            fileHelper = fileHelper
        )
    }
}


@Module(includes = [FileSystemDataModule::class, FileSystemDomainModule::class, FileSystemViewModelModule::class])
class FileSystemModule