package com.example.filesystem.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@Module
@ComponentScan("com.example.filesystem.utils")
internal actual class NativeModule