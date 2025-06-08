package com.coinhub.android.data.dtos.request

import java.math.BigInteger

data class TransferMoneyRequestDto(
    val fromSourceId: String,
    val toSourceId: String,
    val money: BigInteger,
)