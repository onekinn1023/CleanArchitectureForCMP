package com.example.filesystem.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Module
@ComponentScan("com.example.filesystem.utils")
internal actual class NativeModule