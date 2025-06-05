package com.coinhub.android.data.dtos.response

data class NotificationResponseDto(
    val id: String,
    val title: String,
    val body: String,
    val createdAt: String,
    val isRead: Boolean,
)
