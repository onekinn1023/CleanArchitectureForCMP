package com.example.network.utils

interface CloudTypeConverter<I, O> {

    fun convertFromDto(dto: I): O

    fun convertToDto(domain: O): I
}