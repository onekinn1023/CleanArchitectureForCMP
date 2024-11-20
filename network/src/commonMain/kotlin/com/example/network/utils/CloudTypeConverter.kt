package com.example.network.utils


/**
 *  Use @GenerateDto ksp instead of  implementing directly
 */

interface CloudTypeConverter<Domain, Dto> {

    fun convertFromDto(dto: Dto): Domain

    fun convertToDto(domain: Domain): Dto
}