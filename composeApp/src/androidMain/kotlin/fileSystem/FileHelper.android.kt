package fileSystem

import okio.Path
import java.util.UUID

actual fun getUUID(): String {
    return UUID.randomUUID().toString()
}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual abstract class FileHelper {

    actual abstract fun compressFile(outputZipPath: String, sourcePath: String): Path

    actual companion object {
        actual val SYSTEM: FileHelper = run {
            JvmFileHelper()
        }
    }
}