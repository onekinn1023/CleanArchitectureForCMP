package com.example.filesystem.utils

import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import okio.sink
import okio.use
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class JvmFileHelper : FileHelper() {

    override fun compressFile(outputZipPath: String, sourcePath: String): Path {
        val sourceFile = sourcePath.toPath()
        val outputPath = outputZipPath.toPath()
        val fs = FileSystem.SYSTEM
        if (!fs.exists(sourceFile)) throw IllegalArgumentException("not exist!")
        ZipOutputStream(
            fs.sink(outputPath).buffer().outputStream()
        ).use { zos ->
            if (fs.metadata(sourceFile).isDirectory) {
                addDirectoryToZip(zos, fs, sourceFile, sourceFile.name)
            } else {
                zipSourceFile(zos, fs, sourceFile)
            }
        }
        return outputPath
    }

    private fun zipSourceFile(zipOut: ZipOutputStream, fileSystem: FileSystem, sourcePath: Path) {
        fileSystem.read(sourcePath) {
            val zipEntry = ZipEntry(sourcePath.name)
            zipOut.putNextEntry(zipEntry)
            readAll(zipOut.sink().buffer())
            zipOut.closeEntry()
        }
    }

    /**
     * Recursively adds files and directories to the ZIP output stream.
     * @param zipOut The ZIP output stream.
     * @param fileSystem The file system used for reading files.
     * @param sourceDir The directory being compressed.
     * @param basePath The base path in the ZIP file (to maintain folder structure).
     */
    private fun addDirectoryToZip(zipOut: ZipOutputStream, fileSystem: FileSystem, sourceDir: Path, basePath: String) {
        // List all files and directories in the current directory
        fileSystem.list(sourceDir).forEach { filePath ->
            val relativePath = "$basePath/${filePath.name}"

            if (fileSystem.metadata(filePath).isDirectory) {
                // If it's a directory, add a corresponding ZIP entry and recurse
                val dirEntry = ZipEntry("$relativePath/")
                zipOut.putNextEntry(dirEntry)
                zipOut.closeEntry()

                // Recursively add the directory's contents
                addDirectoryToZip(zipOut, fileSystem, filePath, relativePath)
            } else {
                // If it's a file, create a ZIP entry and write the file contents
                val fileEntry = ZipEntry(relativePath)
                zipOut.putNextEntry(fileEntry)
                fileSystem.read(filePath) {
                    readAll(zipOut.sink().buffer())
                }
                zipOut.closeEntry()
            }
        }
    }
}