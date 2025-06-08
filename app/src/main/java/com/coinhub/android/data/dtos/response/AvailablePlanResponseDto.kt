package com.coinhub.android.data.dtos.response

data class AvailablePlanResponseDto(
    val planHistoryId: String,
    val rate: Float,
    val planId: String,
    val days: Int,
)
