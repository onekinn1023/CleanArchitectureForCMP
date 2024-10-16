package com.example.core.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration

/**
 *  17:20:19
 */
fun LocalDateTime.getTime(): String {
    return this.format(
        LocalDateTime.Format {
            hour()
            chars(":")
            minute()
            chars(":")
            second()
        }
    )
}

/**
 *  19/8/2024
 */
fun LocalDateTime.getDate(): String {
    return this.format(
        LocalDateTime.Format {
            dayOfMonth()
            chars("/")
            monthNumber()
            chars("/")
            year()
        }
    )
}

// 20.hours
fun LocalDateTime.plusHours(timeZone: TimeZone, hour: Duration): String {
    return this
        .toInstant(timeZone)
        .plus(hour)
        .toLocalDateTime(timeZone)
        .getTime()
}