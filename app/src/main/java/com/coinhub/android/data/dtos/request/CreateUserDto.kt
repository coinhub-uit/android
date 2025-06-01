package com.coinhub.android.data.dtos.request

import java.util.Date

data class CreateUserDto(
    val id: String,
    val fullName: String,
    val citizenId: String,
    val birthDate: Date,
)
