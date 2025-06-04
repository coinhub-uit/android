package com.coinhub.android.data.dtos.request

import java.util.Date

data class CreateUserRequestDto(
    val id: String,
    val fullName: String,
    val citizenId: String,
    val birthDate: Date,
)
