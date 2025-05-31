package com.coinhub.android.data.models

import java.util.Date

data class TopUpModel(
    val id: String,
    val provider: TopUpProviderEnum,
    val amount: Number,
    val status: TopUpStatusEnum,
    val createdAt: Date,
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