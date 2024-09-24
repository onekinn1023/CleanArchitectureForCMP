package fileSystem

import okio.Path
import platform.Foundation.NSUUID
import platform.UIKit.UIDevice

actual fun getUUID(): String {
    return NSUUID().UUIDString
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual abstract class FileHelper {
    actual abstract fun compressFile(outputZipPath: String, sourcePath: String): Path

    actual companion object {
        val SYSTEM: FileHelper = run {
            NativeFileHelper()
        }
    }
}