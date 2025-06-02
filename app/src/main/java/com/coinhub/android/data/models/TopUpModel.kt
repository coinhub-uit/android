package com.coinhub.android.data.models

import java.math.BigDecimal
import java.time.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class TopUpModel @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val provider: TopUpProviderEnum,
    val amount: BigDecimal,
    val status: TopUpStatusEnum,
    val createdAt: LocalDate,
)

data class CreateTopUpModel @OptIn(ExperimentalUuidApi::class) constructor(
    val url: String,
    val topUpId: Uuid,
)

enum class TopUpProviderEnum {
    VNPAY,
    MOMO,
    ZALOPAY
}

enum class TopUpStatusEnum {
    PROCESSING,
    SUCCESS,
    DECLINED,
    OVERDUE,
}