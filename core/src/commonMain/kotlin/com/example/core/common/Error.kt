package com.example.core.common

interface CommonError

enum class NetworkError : CommonError {
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

enum class LocalError: CommonError {
    UNKNOWN_ERROR,
    SERIALIZATION_ERROR
}
