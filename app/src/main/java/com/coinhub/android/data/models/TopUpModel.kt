package com.coinhub.android.data.models

import java.time.LocalDate

data class TopUpModel(
    val id: String,
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