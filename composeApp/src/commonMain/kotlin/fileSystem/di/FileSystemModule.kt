package fileSystem.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("fileSystem.presentation")
class FileSystemViewModelModule

@Module
@ComponentScan("fileSystem.domain")
class FileSystemDomainModule

@Module
@ComponentScan("FileSystem.data")
class FileSystemDataModule

@Module(includes = [FileSystemDataModule::class, FileSystemDomainModule::class, FileSystemViewModelModule::class])
class FileSystemModule