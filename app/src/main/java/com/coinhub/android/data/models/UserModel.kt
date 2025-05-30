package com.coinhub.android.data.models

import java.time.LocalDate
import java.util.Date
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class UserModel @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val fullName: String,
    val citizenId: String,
    val birthDate: LocalDate,
    val avatar: String?,
    val address: String?,
    val createdAt: Date,
    val deletedAt: Date?,
)
