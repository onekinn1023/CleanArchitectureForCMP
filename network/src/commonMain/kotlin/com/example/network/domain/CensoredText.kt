package com.example.network.domain

import kotlinx.serialization.Serializable

@Serializable
data class CensoredText(
    val result: String
)

