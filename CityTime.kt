package domain

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class CityTime(
    val name: String,
    val timeZone: TimeZone
) {
    fun getNow(): LocalDateTime {
        val now = Clock.System.now()
        return now.toLocalDateTime(timeZone)
    }
}