package com.coinhub.android.data.dtos.request

data class CreateUserRequestDto(
    val id: String,
    val fullName: String,
    val citizenId: String,
    val birthDate: String,
    val address: String?,
    val avatar: String?,
)

data class UpdatePartialUserRequestDto(
    val fullName: String? = null,
    val citizenId: String? = null,
    val birthDate: String? = null,
    val address: String? = null,
)