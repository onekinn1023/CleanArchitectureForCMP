package com.example.core.filesystem.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("com.example.core.filesystem.presentation")
class FileSystemViewModelModule

@Module
@ComponentScan("com.example.core.filesystem.domain")
class FileSystemDomainModule

@Module
@ComponentScan("com.example.core.filesystem.data")
class FileSystemDataModule

@Module(includes = [FileSystemDataModule::class, FileSystemDomainModule::class, FileSystemViewModelModule::class])
class FileSystemModule