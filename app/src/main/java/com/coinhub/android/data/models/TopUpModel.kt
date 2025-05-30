package com.coinhub.android.data.models

import java.util.Date

data class TopUpModel(
    val id: String,
    val provider: TopUpProviderEnum,
    val amount: Number,
    val status: TopUpStatusEnum,
    val createdAt: Date,
)

enum class TopUpProviderEnum(s: String) {
    VNPAY("vnpay"),
    MOMO("momo"),
    ZALOPAY("zalopay")
}

enum class TopUpStatusEnum(s: String) {
    PROCESSING("processing"),
    SUCCESS("success"),
    DECLINED("declined"),
    OVERDUE("overdue"),
}