package com.coinhub.android.data.dtos

import com.coinhub.android.data.models.MethodEnum

data class CreateTicketDto(
    val sourceId: String,
    val methodEnum: MethodEnum,
    val planHistoryId: String,
    val amount: Number,
)
