package com.example.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.datastore.core.DataStoreIndicator
import com.example.datastore.data.SampleText
import com.example.datastore.data.SampleTexts
import com.example.datastore.data.SampleTextsDataStore
import com.example.datastore.data.UserPreferences
import com.example.datastore.data.UserTokenDatastore
import com.example.datastore.data.UserTokens

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DataStoreFactory(private val context: Context) {
    actual fun createExampleDataStore(): DataStore<Preferences> {
        return commonCreateDataStore {
            context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
        }
    }

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