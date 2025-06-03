package com.coinhub.android.data.models

import com.coinhub.android.data.serializers.BigIntegerSerializer
import com.coinhub.android.data.serializers.LocalDateSerializer
import kotlinx.serialization.Serializable
import java.math.BigInteger
import java.time.LocalDate

@Serializable
data class TicketHistoryModel(
    @Serializable(with = LocalDateSerializer::class)
    val issuedAt: LocalDate,
    @Serializable(with = LocalDateSerializer::class)
    val maturedAt: LocalDate,
    @Serializable(with = BigIntegerSerializer::class)
    val principal: BigInteger,
    @Serializable(with = BigIntegerSerializer::class)
    val interest: BigInteger,
)