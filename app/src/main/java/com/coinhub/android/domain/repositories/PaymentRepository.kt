package com.coinhub.android.domain.repositories

import com.coinhub.android.data.dtos.request.CreateTopUpRequestDto
import com.coinhub.android.data.dtos.request.TransferMoneyRequestDto
import com.coinhub.android.domain.models.CreateTopUpModel
import com.coinhub.android.domain.models.TopUpModel

interface PaymentRepository {
    suspend fun transferMoney(dto: TransferMoneyRequestDto)

    suspend fun createTopUp(
        createTopUpDto: CreateTopUpRequestDto,
    ): CreateTopUpModel

    suspend fun getTopUpById(id: String): TopUpModel?
}
