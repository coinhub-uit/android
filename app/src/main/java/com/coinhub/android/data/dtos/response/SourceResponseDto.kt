package com.coinhub.android.data.dtos.response

data class SourceResponseDto(
    val id: String,
    val balance: String,
    val openedAt: String,
    val closedAt: String? = null,
)