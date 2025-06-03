package com.coinhub.android.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlanModel(
    val id: Int,
    val days: Int,
) : Parcelable