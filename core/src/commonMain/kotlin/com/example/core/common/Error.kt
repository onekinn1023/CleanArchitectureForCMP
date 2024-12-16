package com.example.core.common

interface CommonError

sealed interface DataError: CommonError

enum class NetworkError : DataError {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION,
    UNKNOWN;
}

enum class LocalError: DataError {
    UNKNOWN_ERROR,
    SERIALIZATION_ERROR
}
