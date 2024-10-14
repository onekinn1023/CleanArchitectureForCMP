package com.example.core.di

import com.example.core.common.di.CommonModule
import com.example.core.filesystem.di.FileSystemModule
import org.koin.core.annotation.Module

@Module(includes = [CommonModule::class, FileSystemModule::class])
class CoreModule
