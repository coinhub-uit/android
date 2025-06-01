package com.coinhub.android.data.dtos.response

import com.coinhub.android.data.models.TopUpProviderEnum
import com.coinhub.android.data.models.TopUpStatusEnum

data class TopUpDto(
    val id: String,
    val provider: TopUpProviderEnum,
    val amount: Long,
    val status: TopUpStatusEnum,
    val createdAt: String,
)
