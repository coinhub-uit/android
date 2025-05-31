package com.coinhub.android.data.dtos

import com.coinhub.android.data.models.TopUpProviderEnum
import kotlinx.serialization.Serializable

@Serializable
data class CreateTopUpDto(
    val provider: TopUpProviderEnum,
    val returnUrl: String,
    val amount: Long,
    val sourceDestinationId: String,
    val ipAddress: String,
)