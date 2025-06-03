package com.coinhub.android.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDate

@Parcelize
data class TicketHistoryModel(
    val issuedAt: LocalDate,
    val maturedAt: LocalDate,
    val principal: BigInteger,
    val interest: BigInteger,
): Parcelable