package com.coinhub.android.domain.repositories

import com.coinhub.android.data.dtos.request.CreateTopUpDto
import com.coinhub.android.data.dtos.request.TransferMoneyDto
import com.coinhub.android.data.models.CreateTopUpModelResponse

interface PaymentRepository {
    suspend fun transferMoney(dto: TransferMoneyDto)

    suspend fun createTopUp(
        createTopUpDto: CreateTopUpDto,
    ): CreateTopUpModelResponse
}
