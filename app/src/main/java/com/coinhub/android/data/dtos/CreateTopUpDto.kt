package com.coinhub.android.data.dtos

import com.coinhub.android.data.models.TopUpProviderEnum

data class CreateTopUpDto(
    val provider: TopUpProviderEnum,
    val returnUrl: String,
    val amount: Number,
    val sourceDestinationId: String,
    val ipAddress: String,
)