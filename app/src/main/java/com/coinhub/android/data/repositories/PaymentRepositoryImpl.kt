package com.coinhub.android.data.repositories

import com.coinhub.android.common.toCreateTopUpModelResponse
import com.coinhub.android.common.toTopUpModel
import com.coinhub.android.data.api_services.PaymentApiService
import com.coinhub.android.data.dtos.request.CreateTopUpDto
import com.coinhub.android.data.dtos.request.TransferMoneyDto
import com.coinhub.android.data.models.CreateTopUpModel
import com.coinhub.android.data.models.TopUpModel
import com.coinhub.android.domain.repositories.PaymentRepository
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val paymentApiService: PaymentApiService,
) : PaymentRepository {
    override suspend fun transferMoney(dto: TransferMoneyDto) {
        try {
            paymentApiService.transferMoney(dto)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun createTopUp(createTopUpDto: CreateTopUpDto): CreateTopUpModel {
        try {
            val re = paymentApiService.createTopUp(createTopUpDto)
            return re.toCreateTopUpModelResponse()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getTopUpById(id: String): TopUpModel {
        try {
            return paymentApiService.getTopUpById(id).toTopUpModel()
        } catch (e: Exception) {
            throw e
        }
    }
}
