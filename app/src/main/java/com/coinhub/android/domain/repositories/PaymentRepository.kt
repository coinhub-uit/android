package com.coinhub.android.domain.repositories

import com.coinhub.android.data.dtos.request.CreateTopUpDto
import com.coinhub.android.data.dtos.request.TransferMoneyDto

interface PaymentRepository {
    suspend fun transferMoney(dto: TransferMoneyDto)

    suspend fun createTopUp(
        createTopUpDto: CreateTopUpDto,
    ): String
}
