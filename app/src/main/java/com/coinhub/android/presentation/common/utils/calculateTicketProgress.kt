package com.coinhub.android.presentation.common.utils

import com.coinhub.android.domain.models.TicketHistoryModel
import java.time.LocalDate

fun calculateTicketProgress(
    ticketHistory: TicketHistoryModel,
    currentDate: LocalDate = LocalDate.now(),
): Float {
    val issuedAt = ticketHistory.issuedAt
    val maturedAt = ticketHistory.maturedAt

    val totalDays = (maturedAt.toEpochDay() - issuedAt.toEpochDay()).toFloat().coerceAtLeast(1f)
    val daysPassed = (currentDate.toEpochDay() - issuedAt.toEpochDay()).toFloat().coerceAtLeast(0f)

    val progress = (daysPassed / totalDays).coerceIn(0f, 1f)
    return progress
}