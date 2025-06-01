package com.coinhub.android.data.models

import kotlinx.datetime.LocalDate

data class TicketHistoryModel(
    val issuedAt: LocalDate,
    val maturedAt: LocalDate?,
    val principal: Number,
    val interest: Number,
)