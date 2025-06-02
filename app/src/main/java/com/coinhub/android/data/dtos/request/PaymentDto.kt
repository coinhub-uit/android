package com.coinhub.android.data.dtos.request

data class TransferMoneyDto(
    val fromSourceId: String,
    val toSourceId: String,
    val money: Number,
)