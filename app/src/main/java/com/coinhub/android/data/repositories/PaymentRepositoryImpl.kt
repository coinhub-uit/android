package com.coinhub.android.data.repositories

import android.util.Log
import com.coinhub.android.common.toCreateTopUpModelResponse
import com.coinhub.android.common.toTopUpModel
import com.coinhub.android.data.api_services.PaymentApiService
import com.coinhub.android.data.dtos.request.CreateTopUpRequestDto
import com.coinhub.android.data.dtos.request.TransferMoneyRequestDto
import com.coinhub.android.domain.models.CreateTopUpModel
import com.coinhub.android.domain.models.TopUpModel
import com.coinhub.android.domain.repositories.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val paymentApiService: PaymentApiService,
) : PaymentRepository {
    override suspend fun transferMoney(dto: TransferMoneyRequestDto) {
        try {
            paymentApiService.transferMoney(dto)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun createTopUp(createTopUpDto: CreateTopUpRequestDto): CreateTopUpModel {
        try {
            val re = paymentApiService.createTopUp(createTopUpDto)
            return re.toCreateTopUpModelResponse()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTopUpById(id: String): TopUpModel? {
        try {
            return paymentApiService.getTopUpById(id)?.toTopUpModel()
        } catch (e: Exception) {
            throw e
        }
    }
}
