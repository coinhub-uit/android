package com.coinhub.android.data.dtos.request

data class CreateDeviceRequestDto(
    val fcmToken: String,
    val deviceId: String,
)
