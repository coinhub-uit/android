package com.coinhub.android.data.dtos

data class TranferMoneyDto(
    val fromSourceId: String,
    val toSourceId: String,
    val money: Number,
)