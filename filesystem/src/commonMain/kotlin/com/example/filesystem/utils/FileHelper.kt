package com.example.filesystem.utils

import okio.Path
import org.koin.core.annotation.Single

expect fun getUUID(): String

//@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
//expect abstract class FileHelper() {
//
//    abstract fun compressFile(outputZipPath: String, sourcePath: String): Path
//
//    companion object {
//        val SYSTEM: FileHelper
//    }
//}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@Single
expect class FileHelper {
    fun compressFile(outputZipPath: String, sourcePath: String): Path
}