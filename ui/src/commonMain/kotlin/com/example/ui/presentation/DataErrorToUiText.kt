package com.example.ui.presentation

import cmpforlearn.ui.generated.resources.Res
import cmpforlearn.ui.generated.resources.error_conflict
import cmpforlearn.ui.generated.resources.error_no_internet
import cmpforlearn.ui.generated.resources.error_request_timeout
import cmpforlearn.ui.generated.resources.error_serialization
import cmpforlearn.ui.generated.resources.error_server
import cmpforlearn.ui.generated.resources.error_too_many_request
import cmpforlearn.ui.generated.resources.error_unauthorized
import cmpforlearn.ui.generated.resources.error_unknown
import com.example.core.common.DataError
import com.example.core.common.LocalError
import com.example.core.common.NetworkError

fun DataError.toUiText(): UiText {
    val stringRes = when (this) {
        LocalError.UNKNOWN_ERROR -> Res.string.error_unknown
        LocalError.SERIALIZATION_ERROR -> Res.string.error_serialization
        NetworkError.REQUEST_TIMEOUT -> Res.string.error_request_timeout
        NetworkError.UNAUTHORIZED -> Res.string.error_unauthorized
        NetworkError.CONFLICT -> Res.string.error_conflict
        NetworkError.TOO_MANY_REQUESTS -> Res.string.error_too_many_request
        NetworkError.NO_INTERNET -> Res.string.error_no_internet
        NetworkError.PAYLOAD_TOO_LARGE -> Res.string.error_unknown
        NetworkError.SERVER_ERROR -> Res.string.error_server
        NetworkError.SERIALIZATION -> Res.string.error_serialization
        NetworkError.UNKNOWN -> Res.string.error_unknown
    }
    return UiText.StringResourceId(stringRes)
}