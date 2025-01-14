package com.example.datastore

import android.content.Context
import com.example.datastore.data.SampleTextsDataStore
import com.example.datastore.data.UserTokenDatastore

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DataStoreFactory(private val context: Context) {

    actual fun createTokenDataStore(): UserTokenDatastore {
        return UserTokenDatastore {
            context.filesDir.resolve(
                "user_token.json"
            ).absolutePath
        }
    }

    actual fun createSampleDataStore(): SampleTextsDataStore {
        return SampleTextsDataStore {
            context.filesDir.resolve(
                "sample.json"
            ).absolutePath
        }
    }
}