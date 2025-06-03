package com.coinhub.android.data.models

import kotlinx.serialization.Serializable

@Serializable
data class PlanModel(
    val id: Int,
    val days: Int,
)