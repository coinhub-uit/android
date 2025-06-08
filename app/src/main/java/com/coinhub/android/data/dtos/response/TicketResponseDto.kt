package com.coinhub.android.data.dtos.response

import com.coinhub.android.domain.models.MethodEnum
import com.coinhub.android.domain.models.TicketStatus

data class TicketResponseDto(
    val id: Int,
    val openedAt: String,
    val closedAt: String?,
    val status: String,
    val method: String,
    val ticketHistories: List<TicketHistoryResponseDto>,
    val plan: PlanResponseDto,
)