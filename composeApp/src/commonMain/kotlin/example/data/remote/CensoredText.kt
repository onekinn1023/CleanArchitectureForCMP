package example.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class CensoredText(
    val result: String
)
