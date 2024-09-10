package fileSystem

import platform.Foundation.NSUUID
import platform.UIKit.UIDevice

actual fun getUUID(): String {
    return NSUUID().UUIDString
}