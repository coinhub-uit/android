package com.coinhub.android.data.dtos.request

data class RegisterDeviceDto(
    val fcmToken: String,
    val deviceId: String,
)
