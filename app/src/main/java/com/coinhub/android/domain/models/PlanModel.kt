package com.coinhub.android.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class PlanModel(
    val id: Int,
    val days: Int,
)