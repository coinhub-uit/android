package com.coinhub.android.utils

import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.util.Locale

const val DEBOUNCE_TYPING = 500L // milliseconds

const val ACCESS_TOKEN_KEY = "accessToken"

val CURRENCY_FORMAT = NumberFormat.getNumberInstance(Locale.US)

object PreviewDeviceSpecs {
    const val DEVICE = "spec:width=437dp,height=972dp,dpi=395"
    const val WIDTH = 437
    const val HEIGHT = 972
    const val DPI = 395
}