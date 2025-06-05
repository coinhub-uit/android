package com.coinhub.android.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

const val dateTimePattern = "dd/MM/yyyy h:mm a"
const val datePattern = "dd/MM/yyyy"
const val timePattern = "h:mm a"

val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern)
val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(timePattern)
val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(datePattern)

fun Long.toDateString(): String {
    return toLocalDate().format(dateFormatter)
}

fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()
}

fun LocalDate.toDateString(): String {
    return this.format(dateFormatter)
}

fun LocalDate.toMillis(): Long {
    return this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

fun LocalDateTime.toDateString(): String {
    return this.format(dateTimeFormatter)
}

fun ZonedDateTime.toDateString(): String {
    return this.format(dateTimeFormatter)
}

fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(this, dateFormatter)
}
