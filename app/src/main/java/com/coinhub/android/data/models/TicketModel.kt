package com.coinhub.android.data.models

import java.time.LocalDate

data class TicketModel(
    val id: Number,
    val openedAt: LocalDate,
    val closedAt: LocalDate?,
    val status: TicketStatus,
    val method: MethodEnum,
    val ticketHistories: List<TicketHistoryModel>,
    val plan: PlanModel,
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