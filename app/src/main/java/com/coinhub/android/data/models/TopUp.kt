package com.coinhub.android.data.models

import java.util.Date

data class TopUp(
    val id: String,
    val provider: TopUpProviderEnum,
    val amount: Number,
    val status: TopUpStatusEnum,
    val createdAt: Date,
)

enum class TopUpProviderEnum(s: String) {
    MOMO("momo"),
    VNPAY("vnpay"),
    ZALOPAY("zalopay")
}

enum class TopUpStatusEnum(s: String) {
    PROCESSING("processing"),
    SUCCESS("success"),
    DECLINED("declined"),
    OVERDUE("overdue"),
}