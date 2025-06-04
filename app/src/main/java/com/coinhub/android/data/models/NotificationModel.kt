package com.coinhub.android.data.models

import java.time.LocalDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class NotificationModel @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val title: String,
    val body: String,
    val createdAt: LocalDateTime,
    val isRead: Boolean
)