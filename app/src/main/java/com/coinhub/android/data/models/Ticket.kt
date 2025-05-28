package com.coinhub.android.data.models

import java.util.Date

data class Ticket(
    val id: String,
    val openedAt: Date,
    val closedAt: Date?,
    val status: TicketStatus,
    val method: MethodEnum,
)

enum class TicketStatus(s: String) {
    ACTIVE("active"),
    EARLYWITHDRAWN("earlyWithdrawn"),
    MATUREDWITHDRAWN("maturedWithdrawn"),
}

enum class MethodEnum(s: String) {
    NR("NR"),
    PR("PR"),
    PIR("PIR"),
}