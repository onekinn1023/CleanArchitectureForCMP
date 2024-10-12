package com.example.core.filesystem.utils

import okio.Path
import platform.Foundation.NSUUID

actual fun getUUID(): String {
    return NSUUID().UUIDString
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual abstract class FileHelper actual constructor() {
    actual abstract fun compressFile(
        outputZipPath: String,
        sourcePath: String
    ): Path

    actual companion object {
        actual val SYSTEM: FileHelper by lazy {
            NativeFileHelper()
        }
    }
}