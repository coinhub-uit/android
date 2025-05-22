package com.coinhub.android.authentication.data.dtos

import java.util.Date
import kotlin.uuid.ExperimentalUuidApi

data class CreateUserDto @OptIn(ExperimentalUuidApi::class) constructor(
    val id: String,

    val fullName: String,

    val citizenId: String,

    val birthDate: Date,
)
