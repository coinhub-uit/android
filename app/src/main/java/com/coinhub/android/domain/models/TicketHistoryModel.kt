package com.coinhub.android.domain.models

import java.math.BigInteger
import java.time.LocalDate

data class TicketHistoryModel(
    val issuedAt: LocalDate,
    val maturedAt: LocalDate,
    val principal: BigInteger,
    val interest: BigInteger,
)