package com.coinhub.android.domain.models

import com.coinhub.android.data.serializers.BigIntegerSerializer
import com.coinhub.android.data.serializers.ZonedDateTimeSerializer
import kotlinx.serialization.Serializable
import java.math.BigInteger
import java.time.ZonedDateTime

@Serializable
data class SourceModel(
    val id: String,
    @Serializable(with = BigIntegerSerializer::class)
    val balance: BigInteger,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val openedAt: ZonedDateTime,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val closedAt: ZonedDateTime? = null
)