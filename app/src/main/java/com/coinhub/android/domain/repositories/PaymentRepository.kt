package com.coinhub.android.domain.repositories

import com.coinhub.android.data.dtos.TransferMoneyDto

interface PaymentRepository {
    suspend fun transferMoney(dto: TransferMoneyDto)
}
