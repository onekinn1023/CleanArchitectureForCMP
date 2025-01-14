package com.example.datastore

import com.example.datastore.data.SampleTextsDataStore
import com.example.datastore.data.UserTokenDatastore
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

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DataStoreFactory {
    actual fun createTokenDataStore(): UserTokenDatastore {
        return UserTokenDatastore {
            "${fileDirectory()}/user_token.json"
        }
    }

    actual fun createSampleDataStore(): SampleTextsDataStore {
        return SampleTextsDataStore {
            "${fileDirectory()}/sample.json"
        }
    }
}