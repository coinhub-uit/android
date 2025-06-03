package com.coinhub.android.data.models

import com.coinhub.android.data.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class TicketModel(
    val id: Int,
    @Serializable(with = LocalDateSerializer::class)
    val openedAt: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
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

// TODO: Just ...
enum class MethodEnum(val description: String, val longDescription: String) {
    NR(
        "Non Rollover",
        "The interest is will be given to the user at the end of the plan, and the ticket will be closed."
    ),
    PR(
        "Principal Rollover",
        "Principal will be rolled over to the next plan, and the interest will be given to the user at the end of the plan."
    ),
    PIR(
        "Principal & Interest Rollover",
        "Principal and interest will be rolled over to the next plan. No interest will be given to the user at the end of the plan until user withdraw."
    ),
}