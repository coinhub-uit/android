package com.coinhub.android.data.models

import kotlin.uuid.ExperimentalUuidApi

data class UserModel @OptIn(ExperimentalUuidApi::class) constructor(
    val id: String,
    val fullName: String,
    val citizenId: String,
    val birthDate: String,
    val avatar: String?,
    val address: String?,
    val createdAt: String,
    val deletedAt: String?,
)
