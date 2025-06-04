package com.coinhub.android.data.dtos.request

import com.coinhub.android.data.models.MethodEnum

data class CreateTicketRequestDto(
    val sourceId: String,
    val methodEnum: MethodEnum,
    val planHistoryId: String,
    val amount: Long,
)
