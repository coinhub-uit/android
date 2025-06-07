package com.coinhub.android.domain.models

import java.time.ZonedDateTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class NotificationModel @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val title: String,
    val body: String,
    val createdAt: ZonedDateTime,
    val isRead: Boolean,
)