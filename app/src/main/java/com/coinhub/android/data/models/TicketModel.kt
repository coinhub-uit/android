package com.coinhub.android.data.models

import java.util.Date

data class TicketModel(
    val id: String,
    val openedAt: Date,
    val closedAt: Date?,
    val status: TicketStatus,
    val method: MethodEnum,
)

enum class TicketStatus(s: String) {
    ACTIVE("active"),
    EARLY_WITH_DRAWN("earlyWithdrawn"),
    MATURED_WITH_DRAWN("maturedWithdrawn"),
}

enum class MethodEnum(s: String) {
    NR("NR"),
    PR("PR"),
    PIR("PIR"),
}