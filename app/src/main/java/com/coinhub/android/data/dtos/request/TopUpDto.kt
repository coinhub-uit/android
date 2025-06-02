package com.coinhub.android.data.dtos.request

import com.coinhub.android.data.models.TopUpProviderEnum

data class CreateTopUpDto(
    val provider: TopUpProviderEnum,
    val returnUrl: String,
    val amount: Long,
    val sourceDestinationId: String,
    val ipAddress: String,
)