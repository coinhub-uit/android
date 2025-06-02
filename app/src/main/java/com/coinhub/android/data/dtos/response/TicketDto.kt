package com.coinhub.android.data.dtos.response

import com.coinhub.android.data.models.MethodEnum
import com.coinhub.android.data.models.TicketStatus

data class TicketDto(
    val id: Int,
    val openedAt: String,
    val closedAt: String?,
    val status: TicketStatus,
    val method: MethodEnum,
    val ticketHistories: List<TicketHistoryDto>,
    val plan: PlanDto,
)