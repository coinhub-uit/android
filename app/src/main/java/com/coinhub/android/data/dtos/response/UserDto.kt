package com.coinhub.android.data.dtos.response

data class UserDto(
    val id: String,
    val fullName: String,
    val citizenId: String,
    val birthDate: String,
    val avatar: String?,
    val address: String?,
    val createdAt: String,
    val deletedAt: String?,
)