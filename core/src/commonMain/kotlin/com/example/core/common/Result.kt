package com.example.core.common


sealed interface Result<out D, out E : CommonError> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : CommonError>(val error: E) : Result<Nothing, E>
}

inline fun <T, E : CommonError, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

fun <T, E: CommonError> Result<T, E>.asEmptyDataResult(): EmptyResult<E> {
    return map { }
}

inline fun <T, E: CommonError> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}

inline fun <T, E: CommonError> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> {
            action(error)
            this
        }

        is Result.Success -> this
    }
}

fun <T, E: CommonError> Result<T, E>.getOrThrow(): T {
    return when (this) {
        is Result.Error -> throw Exception("Cannot get example.data!")
        is Result.Success -> {
            this.data
        }
    }
}

fun <T, E: CommonError> Result<T, E>.isSuccess(): Boolean {
    return when (this) {
        is Result.Error -> false
        is Result.Success -> true
    }
}

fun <T, E: CommonError> Result<T, E>.getOrNull(): T? {
    return when (this) {
        is Result.Error -> null
        is Result.Success -> {
            this.data
        }
    }
}

typealias EmptyResult<E> = Result<Unit, E>