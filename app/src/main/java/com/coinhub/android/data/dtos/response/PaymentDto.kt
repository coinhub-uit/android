package com.coinhub.android.data.dtos.response

import com.coinhub.android.data.models.TopUpProviderEnum
import com.coinhub.android.data.models.TopUpStatusEnum
import java.math.BigInteger

data class TopUpDto(
    val id: String,
    val provider: TopUpProviderEnum,
    val amount: String,
    val status: TopUpStatusEnum,
    val createdAt: String,
)

data class CreateTopUpResponseDto(
    val url: String,
    val topUpId: String,
)