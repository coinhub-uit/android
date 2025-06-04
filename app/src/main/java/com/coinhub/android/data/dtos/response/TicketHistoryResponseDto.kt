package com.coinhub.android.data.dtos.response

data class TicketHistoryResponseDto(
    val issuedAt: String,
    val maturedAt: String,
    val principal: String,
    val interest: String,
)
