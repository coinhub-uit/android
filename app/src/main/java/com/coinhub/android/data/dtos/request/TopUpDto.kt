package com.coinhub.android.data.dtos.request

import java.math.BigInteger

data class CreateTopUpDto(
    val provider: String,
    val returnUrl: String,
    val amount: BigInteger,
    val sourceDestinationId: String,
    val ipAddress: String,
)