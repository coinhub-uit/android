package com.coinhub.android.data.dtos.request

import com.coinhub.android.data.models.TopUpProviderEnum
import java.math.BigInteger

data class CreateTopUpDto(
    val provider: TopUpProviderEnum,
    val returnUrl: String,
    val amount: BigInteger,
    val sourceDestinationId: String,
    val ipAddress: String,
)