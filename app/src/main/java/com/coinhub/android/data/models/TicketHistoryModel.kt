package com.coinhub.android.data.models

import kotlinx.datetime.LocalDate
import java.math.BigDecimal

data class TicketHistoryModel(
    val issuedAt: LocalDate,
    val maturedAt: LocalDate?,
    val principal: BigDecimal,
    val interest: BigDecimal,
)