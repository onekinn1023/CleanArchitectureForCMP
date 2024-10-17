package com.example.core.utils

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun initNapierLog(logEnable: Boolean) {
    if (logEnable) {
        Napier.base(DebugAntilog())
    }
}