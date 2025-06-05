package com.coinhub.android.data.models

import java.math.BigInteger
import java.time.ZonedDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class TopUpModel @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val provider: ProviderEnum,
    val amount: BigInteger,
    val status: StatusEnum,
    val createdAt: ZonedDateTime,
) {
    enum class ProviderEnum(val displayName: String) {
        vnpay("VNPay"),
        momo("Momo"),
        zalo("ZaloPay"),
    }

    enum class StatusEnum {
        proccesing,
        success,
        declined,
        overdue,
    }
}

data class CreateTopUpModel @OptIn(ExperimentalUuidApi::class) constructor(
    val url: String,
    val topUpId: Uuid,
)