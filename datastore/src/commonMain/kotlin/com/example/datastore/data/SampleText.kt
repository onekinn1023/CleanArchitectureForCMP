package com.example.datastore.data

import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioSerializer
import androidx.datastore.core.okio.OkioStorage
import com.example.datastore.core.DataStoreIndicator
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import okio.use

typealias SampleTexts = List<SampleText>

@Serializable
data class SampleText(
    val sampleKey: String,
    val text: String
)

internal object SampleTextSerializer : OkioSerializer<SampleTexts> {
    override val defaultValue: SampleTexts
        get() = emptyList()

    override suspend fun readFrom(source: BufferedSource): SampleTexts {
        return Json.decodeFromString(source.readUtf8())
    }

    override suspend fun writeTo(t: SampleTexts, sink: BufferedSink) {
        sink.use {
            it.writeUtf8(Json.encodeToString(t))
        }
    }
}

class SampleTextsDataStore(
    private val produceFilePath: () -> String
) : DataStoreIndicator<SampleTexts, SampleText> {

    private val db = DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = SampleTextSerializer,
            producePath = {
                produceFilePath().toPath()
            }
        )
    )

    override val data: Flow<SampleTexts>
        get() = db.data

    override suspend fun clear() {
        db.updateData {
            emptyList()
        }
    }

    override suspend fun remove(i: SampleText) {
        db.updateData {
            it.filter { sampleText -> sampleText.sampleKey != i.sampleKey }
        }
    }

    override suspend fun upsert(i: SampleText) {
        db.updateData {
            it.map { sampleText ->
                if (sampleText.sampleKey != i.text) {
                    sampleText
                } else {
                    sampleText.copy(
                        text = i.text
                    )
                }
            }
        }
    }
}
