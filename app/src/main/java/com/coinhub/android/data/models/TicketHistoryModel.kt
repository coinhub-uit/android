package com.coinhub.android.data.models

data class TicketHistoryModel(
    val issuedAt: String,
    val maturedAt: String?,
    val principal: String,
    val interest: String,
)