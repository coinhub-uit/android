package com.coinhub.android.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Parcelize
data class User @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid,
    val fullName: String,
    val citizenId: String,
    val birthDate: Date,
    val avatar: String?,
    val address: String?,
    val createdAt: Date,
    val deletedAt: Date?
): Parcelable
