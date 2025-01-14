package com.example.datastore.data

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioSerializer
import androidx.datastore.core.okio.OkioStorage
import com.example.datastore.utils.Encrypt
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import okio.use
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@Serializable
data class UserTokens(
    val preferences: List<UserPreferences>
)

@Serializable
data class UserPreferences(
    val user: String = "",
    val token: String? = null
)

@OptIn(ExperimentalEncodingApi::class)
internal object UserTokenSerializer : OkioSerializer<UserTokens> {
    override val defaultValue: UserTokens
        get() = UserTokens(
            preferences = emptyList()
        )

    override suspend fun readFrom(source: BufferedSource): UserTokens {
        val encryptedBytes = source.readByteArray()
        val encryptedByteDecoded = Base64.decode(encryptedBytes)
        val decryptedBytes = Encrypt.decrypt(
            key = UserTokens::class.simpleName.orEmpty(),
            bytes = encryptedByteDecoded
        )
        val decodeJsonString = decryptedBytes.decodeToString()
        return Json.decodeFromString(decodeJsonString)
    }

    override suspend fun writeTo(t: UserTokens, sink: BufferedSink) {
        val jsonBytes = Json.encodeToString(UserTokens.serializer(), t).encodeToByteArray()
        val encryptedBytes = Encrypt.encrypt(
            key = UserTokens::class.simpleName.orEmpty(),
            bytes = jsonBytes
        )
        val encryptedByteBase64 = Base64.encodeToByteArray(encryptedBytes)
        sink.use {
            it.write(encryptedByteBase64)
        }
    }

}

class UserTokenDatastore(
    private val produceFilePath: () -> String
) : DataStoreIndicator<UserTokens, UserPreferences> {

    private val db = DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = UserTokenSerializer,
            producePath = {
                produceFilePath().toPath()
            },
        )
    )

    override val data: Flow<UserTokens>
        get() = db.data

    override suspend fun remove(i: UserPreferences) {
        db.updateData {
            val newItems = it.preferences.toMutableList()
            UserTokens(
                preferences = newItems.apply {
                    remove(i)
                }
            )
        }
    }

    override suspend fun upsert(i: UserPreferences) {
        db.updateData { prev ->
            val newItem = prev.preferences.map {
                if (it.user == it.user) {
                    i
                } else {
                    it
                }
            }
            UserTokens(
                preferences = newItem
            )
        }
    }

    override suspend fun clear() {
        db.updateData {
            UserTokens(
                emptyList()
            )
        }
    }
}
