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
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import retrofit2.HttpException
import java.math.BigInteger

class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val preferenceDataStore: PreferenceDataStore,
) : UserRepository {
    private val mutex = Mutex()

    private var userModel: UserModel? = null
    private var ticketModels: List<TicketModel>? = null
    private var sourceModels: List<SourceModel>? = null
    private var notificationModels: List<NotificationModel>? = null

    override suspend fun getUserById(id: String, refresh: Boolean): UserModel? {
        if (refresh || userModel == null) {
            try {
                val user = userApiService.getUserById(id).toUserModel()
                mutex.withLock {
                    this.userModel = user
                }
            } catch (e: Exception) {
                throw e
            }
        }
        return mutex.withLock {
            userModel
        }
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

    override suspend fun delete(id: String) {
        return try {
            userApiService.delete(id)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getUserSources(id: String, refresh: Boolean): List<SourceModel> {
        if (refresh || sourceModels == null) {
            try {
                val sources = userApiService.getUserSources(id).filter {
                    it.closedAt == null // NOTE: Filter out closed sources
                }.map {
                    it.toSourceModel()
                }
                mutex.withLock {
                    sourceModels = sources
                }
                return sources
            } catch (e: Exception) {
                throw e
            }
        } else {
            return mutex.withLock {
                sourceModels ?: emptyList()
            }
        }
    }

    override suspend fun getUserTickets(id: String, refresh: Boolean): List<TicketModel> {
        if (refresh || ticketModels == null) {
            try {
                val tickets = userApiService.getUserTickets(id).map {
                    it.toTicketModel()
                }
                saveTotalPrincipalAndInterest(tickets)
                mutex.withLock {
                    ticketModels = tickets
                }
            } catch (e: HttpException) {
                return emptyList()
            }
        }
        return mutex.withLock {
            ticketModels ?: emptyList()
        }
    }

    override suspend fun getUserNotification(id: String, refresh: Boolean): List<NotificationModel> {
        if (refresh || notificationModels == null) {
            try {
                val notifications = userApiService.getUserNotification(id).map {
                    it.toNotificationModel()
                }
                mutex.withLock {
                    notificationModels = notifications
                }
            } catch (e: Exception) {
                throw e
            }
        }
        return mutex.withLock {
            notificationModels ?: emptyList()
        }
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

    suspend fun clearCache() {
        mutex.withLock {
            userModel = null
            ticketModels = null
            sourceModels = null
            notificationModels = null
        }
    }
}
