package com.coinhub.android.data.models

import java.time.LocalDate

data class TicketModel(
    val id: Int,
    val openedAt: LocalDate,
    val closedAt: LocalDate?,
    val status: TicketStatus,
    val method: MethodEnum,
    val ticketHistories: List<TicketHistoryModel>,
    val plan: PlanModel,
)

enum class TicketStatus(val description: String) {
    ACTIVE("Active"),
    EARLY_WITH_DRAWN("Early Withdrawn"),
    MATURED_WITH_DRAWN("Matured Withdrawn"),
}

enum class MethodEnum(val description: String) {
    NR("Non-rollover"),
    PR("Principal Rollover"),
    PIR("Principal & Interest Rollover"),
}