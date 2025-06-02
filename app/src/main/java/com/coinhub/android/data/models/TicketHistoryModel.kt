package com.coinhub.android.data.models

import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDate

data class TicketHistoryModel(
    val issuedAt: LocalDate,
    val maturedAt: LocalDate,
    val principal: BigInteger,
    val interest: BigInteger,
)