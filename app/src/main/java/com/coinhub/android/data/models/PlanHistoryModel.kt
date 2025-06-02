package com.coinhub.android.data.models

import java.time.LocalDate

data class PlanHistoryModel(
    val id: Number,
    val createdAt: LocalDate,
    val rate: Number,
)