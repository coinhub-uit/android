package com.coinhub.android.data.models

import kotlinx.datetime.LocalDate
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class TopUpModel @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val provider: TopUpProviderEnum,
    val amount: Long,
    val status: TopUpStatusEnum,
    val createdAt: LocalDate,
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