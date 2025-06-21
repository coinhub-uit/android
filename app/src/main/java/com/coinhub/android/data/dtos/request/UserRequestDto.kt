package com.coinhub.android.data.dtos.request

import java.util.Date

data class CreateUserRequestDto(
    val id: String,
    val fullName: String,
    val citizenId: String,
    val birthDate: Date,
    val address: String?,
    val avatar: String?,
)

data class UpdatePartialUserRequestDto(
    val fullName: String? = null,
    val citizenId: String? = null,
    val birthDate: Date? = null,
    val address: String? = null,
)