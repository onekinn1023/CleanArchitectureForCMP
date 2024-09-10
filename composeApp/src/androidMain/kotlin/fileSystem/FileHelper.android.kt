package fileSystem

import java.util.UUID

actual fun getUUID(): String {
    return UUID.randomUUID().toString()
}