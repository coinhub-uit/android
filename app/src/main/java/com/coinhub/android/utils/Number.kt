package com.coinhub.android.utils

import java.text.NumberFormat
import java.util.Locale

val CURRENCY_FORMAT = NumberFormat.getNumberInstance(Locale.US)

fun Number.toVndFormat(space: Boolean = false, fullFormat: Boolean = true): String {
    return CURRENCY_FORMAT.format(this) + if (space) " " else "" + if (fullFormat) "VNĐ" else "Đ"
}

