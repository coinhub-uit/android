package com.coinhub.android.data.dtos.response

data class TopUpResponseDto(
    val id: String,
    val provider: String,
    val amount: String,
    val status: String,
    val createdAt: String,
)

