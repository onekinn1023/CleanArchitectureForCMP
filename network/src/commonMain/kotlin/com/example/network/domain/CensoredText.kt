package com.example.network.domain

import com.example.core.utils.GenerateDto
import kotlinx.serialization.Serializable

@Serializable
@GenerateDto
data class CensoredText(
    val result: String
)

