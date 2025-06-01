package com.coinhub.android.data.dtos

data class TransferMoneyDto(
    val fromSourceId: String,
    val toSourceId: String,
    val money: Number,
)