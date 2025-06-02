package com.coinhub.android.data.models

import java.math.BigDecimal
import java.time.LocalDate

data class TicketHistoryModel(
    val issuedAt: LocalDate,
    val maturedAt: LocalDate?,
    val principal: BigDecimal,
    val interest: BigDecimal,
)