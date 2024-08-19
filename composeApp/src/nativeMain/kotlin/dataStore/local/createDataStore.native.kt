package dataStore.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
private fun fileDirectory(): String {
    val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory).path!!
}

//actual fun createDataStore(): DataStore<Preferences> {
//    return commonCreateDataStore {
//        fileDirectory() + "/$DATA_STORE_FILE_NAME"
//    }
//}

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DataStoreFactory {
    actual fun createExampleDataStore(): DataStore<Preferences> {
        return commonCreateDataStore {
            fileDirectory() + "/$DATA_STORE_FILE_NAME"
        }
    }
}