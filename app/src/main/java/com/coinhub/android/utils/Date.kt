package com.coinhub.android.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

const val dateTimePattern = "dd/MM/yyyy h:mm a"
const val datePattern = "dd/MM/yyyy"
const val timePattern = "h:mm a"

val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(dateTimePattern)
val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(timePattern)
val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(datePattern)

fun Long.toDateString(): String {
    val formatter = SimpleDateFormat(datePattern, Locale.getDefault())
    return formatter.format(Date(this))
}

fun Long.toLocalDate(): LocalDate {
    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

fun LocalDate.toDateString(): String {
    return this.format(dateFormatter)
}

fun LocalDate.toMillis(): Long {
    return this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}
