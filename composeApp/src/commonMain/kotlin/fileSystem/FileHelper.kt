package fileSystem

import kotlinx.datetime.Clock
import okio.Path

expect fun getUUID(): String

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect abstract class FileHelper() {

    abstract fun compressFile(outputZipPath: String, sourcePath: String): Path

    companion object {
        val SYSTEM: FileHelper
    }
}