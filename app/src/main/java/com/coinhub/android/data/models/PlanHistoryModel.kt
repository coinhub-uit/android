package com.coinhub.android.data.models

import kotlinx.datetime.LocalDate

data class PlanHistoryModel(
    val id: Number,
    val createdAt: LocalDate,
    val rate: Number,
)