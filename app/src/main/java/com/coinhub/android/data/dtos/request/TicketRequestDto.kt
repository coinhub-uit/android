package com.coinhub.android.data.dtos.request

data class CreateTicketRequestDto(
    val sourceId: String,
    val method: String,
    val planHistoryId: Int,
    val amount: Long,
)
