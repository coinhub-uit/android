package com.coinhub.android.utils

import java.text.NumberFormat
import java.util.Locale

val CURRENCY_FORMAT: NumberFormat = NumberFormat.getNumberInstance(Locale.US)

enum class CurrencySymbol(val symbol: String) {
    VND("VNĐ"), D("Đ"),
}

// I will refactor for a custom type if I have timeeeee
fun Number.toVndFormat(space: Boolean? = null, currencySymbol: CurrencySymbol? = null): String {
    return CURRENCY_FORMAT.format(this) + (if (space == true || (space == null && currencySymbol != null)) " " else "") + (currencySymbol?.symbol
        ?: "")
}

fun Number.toPercentFormat(): String {
    val percentFormat = NumberFormat.getPercentInstance(Locale.US)
    percentFormat.maximumFractionDigits = 2
    return percentFormat.format(this.toDouble())
}