package com.coinhub.android.domain.repositories

import com.coinhub.android.data.dtos.request.CreateTopUpDto
import com.coinhub.android.data.dtos.request.TransferMoneyDto
import com.coinhub.android.data.models.CreateTopUpModel
import com.coinhub.android.data.models.TopUpModel

interface PaymentRepository {
    suspend fun transferMoney(dto: TransferMoneyDto)

    suspend fun createTopUp(
        createTopUpDto: CreateTopUpDto,
    ): CreateTopUpModel

    suspend fun getTopUpById(id: String): TopUpModel
}
