package com.coinhub.android.data.repositories

import com.coinhub.android.common.toDeviceModel
import com.coinhub.android.common.toNotificationModel
import com.coinhub.android.common.toSourceModel
import com.coinhub.android.common.toTicketModel
import com.coinhub.android.common.toUserModel
import com.coinhub.android.data.api_services.UserApiService
import com.coinhub.android.data.dtos.request.CreateDeviceRequestDto
import com.coinhub.android.data.dtos.request.CreateUserRequestDto
import com.coinhub.android.domain.models.DeviceModel
import com.coinhub.android.domain.models.NotificationModel
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.domain.models.TicketModel
import com.coinhub.android.domain.models.UserModel
import com.coinhub.android.domain.repositories.PreferenceDataStore
import com.coinhub.android.domain.repositories.UserRepository
import jakarta.inject.Inject
import java.math.BigInteger

class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val preferenceDataStore: PreferenceDataStore,
) : UserRepository {
    private var userModel: UserModel? = null
    private var ticketModels: List<TicketModel>? = null
    private var sourceModels: List<SourceModel>? = null
    private var notificationModels: List<NotificationModel>? = null

    override suspend fun getUserById(id: String, refresh: Boolean): UserModel? {
        if (refresh || userModel == null) {
            try {
                userApiService.getUserById(id).toUserModel()
                this.userModel = userApiService.getUserById(id).toUserModel()
            } catch (e: Exception) {
                throw e
            }
        }
        return this.userModel
    }

    override suspend fun registerProfile(user: CreateUserRequestDto): UserModel {
        return try {
            userApiService.registerProfile(user).toUserModel()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateProfile(id: String, user: CreateUserRequestDto) {
        try {
            userApiService.updateProfile(id, user)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun delete(id: String): UserModel {
        return try {
            userApiService.delete(id).toUserModel()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getUserSources(id: String, refresh: Boolean): List<SourceModel>? {
        if (refresh || sourceModels == null) {
            try {
                sourceModels = userApiService.getUserSources(id).map {
                    it.toSourceModel()
                }
            } catch (e: Exception) {
                throw e
            }
        }
        return sourceModels
    }

    override suspend fun getUserTickets(id: String, refresh: Boolean): List<TicketModel> {
        if (refresh || ticketModels == null) {
            try {
                ticketModels = userApiService.getUserTickets(id).map {
                    it.toTicketModel()
                }
            } catch (e: Exception) {
                throw e
            }
        }
        saveTotalPrincipalAndInterest(ticketModels!!)
        return ticketModels!!
    }

    override suspend fun getUserNotification(id: String, refresh: Boolean): List<NotificationModel> {
        if (refresh || notificationModels == null) {
            try {
                notificationModels = userApiService.getUserNotification(id).map {
                    it.toNotificationModel()
                }
            } catch (e: Exception) {
                throw e
            }
        }
        return notificationModels!!
    }

    override suspend fun registerDevice(id: String, dto: CreateDeviceRequestDto): DeviceModel {
        return try {
            userApiService.registerDevice(id, dto).toDeviceModel()
        } catch (e: Exception) {
            throw e
        }
    }

    private suspend fun saveTotalPrincipalAndInterest(tickets: List<TicketModel>) {
        val totalPrincipal = tickets.sumOf { ticket ->
            ticket.ticketHistories.firstOrNull()?.principal ?: BigInteger.ZERO
        }
        val totalInterest = tickets.sumOf { ticket ->
            ticket.ticketHistories.firstOrNull()?.interest ?: BigInteger.ZERO
        }
        preferenceDataStore.saveTotalPrincipal(totalPrincipal)
        preferenceDataStore.saveTotalInterest(totalInterest)
    }
}
