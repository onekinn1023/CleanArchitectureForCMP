package com.example.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DataStoreFactory(private val context: Context) {
    actual fun createExampleDataStore(): DataStore<Preferences> {
        return commonCreateDataStore {
            context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath
        }
    }
}