package com.example.core.filesystem.data

import io.ktor.http.ContentType

class FileInfo(
    val name: String,
    val mimeType: String,
    val bytes: ByteArray
)

fun FileInfo.getMimeType(): ContentType {
    return when (mimeType) {
        "jpg", "jpeg" -> ContentType.Image.JPEG
        "png" -> ContentType.Image.PNG
        "gif" -> ContentType.Image.GIF
        "pdf" -> ContentType.Application.Pdf
        "txt" -> ContentType.Text.Plain
        else -> ContentType.Application.OctetStream
    }
}