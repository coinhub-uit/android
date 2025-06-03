package com.coinhub.android.data.models

import com.coinhub.android.data.serializers.BigIntegerSerializer
import kotlinx.serialization.Serializable
import java.math.BigInteger

@Serializable
data class SourceModel(
    val id: String,
    @Serializable(with = BigIntegerSerializer::class)
    val balance: BigInteger,
)