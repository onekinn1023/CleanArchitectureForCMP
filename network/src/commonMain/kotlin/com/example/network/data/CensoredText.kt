package com.example.network.data

import com.example.network.domain.CensoredText
import com.example.network.utils.CloudTypeConverter
import kotlinx.serialization.Serializable

@Serializable
data class CensoredTextDto(
    val result: String
)

class CensoredTextTypeConverter : CloudTypeConverter<CensoredTextDto, CensoredText> {

    override fun convertFromDto(dto: CensoredTextDto): CensoredText {
        return CensoredText(
            result = dto.result
        )
    }

    override fun convertToDto(domain: CensoredText): CensoredTextDto {
        return CensoredTextDto(
            result = domain.result
        )
    }
}