package com.coinhub.android.data.models

data class TicketModel(
    val id: String,
    val openedAt: String,
    val closedAt: String?,
    val status: TicketStatus,
    val method: MethodEnum,
    val ticketHistory: List<TicketHistoryModel>,
)

enum class TicketStatus {
    ACTIVE,
    EARLY_WITH_DRAWN,
    MATURED_WITH_DRAWN,
}

enum class MethodEnum {
    NR,
    PR,
    PIR,
}