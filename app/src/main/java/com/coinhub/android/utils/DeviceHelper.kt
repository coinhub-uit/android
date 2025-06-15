package com.coinhub.android.utils

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DeviceHelper @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    @SuppressLint("HardwareIds")
    fun generateDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
}