package com.coinhub.android.data.dtos.response

import com.coinhub.android.data.models.MethodEnum
import com.coinhub.android.data.models.TicketStatus

data class TicketResponseDto(
    val id: Int,
    val openedAt: String,
    val closedAt: String?,
    val status: TicketStatus,
    val method: MethodEnum,
    val ticketHistories: List<TicketHistoryResponseDto>,
    val plan: PlanResponseDto,
)