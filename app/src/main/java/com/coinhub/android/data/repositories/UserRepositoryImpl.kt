package com.coinhub.android.data.repositories

import com.coinhub.android.common.toDeviceModel
import com.coinhub.android.common.toSourceModel
import com.coinhub.android.common.toTicketModel
import com.coinhub.android.common.toUserModel
import com.coinhub.android.data.api_services.UserApiService
import com.coinhub.android.data.dtos.request.CreateDeviceRequestDto
import com.coinhub.android.data.dtos.request.CreateUserRequestDto
import com.coinhub.android.data.models.DeviceModel
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.data.models.UserModel
import com.coinhub.android.domain.repositories.UserRepository
import jakarta.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
) : UserRepository {

    private var userModel: UserModel? = null
    private var ticketModels: List<TicketModel>? = null
    private var sourceModels: List<SourceModel>? = null

    override suspend fun getUserById(id: String, refresh: Boolean): UserModel {
        if (refresh || userModel == null) {
            try {
                userApiService.getUserById(id).toUserModel()
                this.userModel = userApiService.getUserById(id).toUserModel()
            } catch (e: Exception) {
                throw e
            }
        }
        return this.userModel!!
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

    override suspend fun updatePartialProfile(id: String, user: CreateUserRequestDto): UserModel {
        return try {
            userApiService.updatePartialProfile(id, user).toUserModel()
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

    override suspend fun getUserSources(id: String, refresh: Boolean): List<SourceModel> {
        if (refresh || sourceModels == null) {
            try {
                sourceModels = userApiService.getUserSources(id).map {
                    it.toSourceModel()
                }
            } catch (e: Exception) {
                throw e
            }
        }
        return sourceModels!!
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
        return ticketModels!!
    }

    override suspend fun registerDevice(id: String, dto: CreateDeviceRequestDto): DeviceModel {
        return try {
            userApiService.registerDevice(id, dto).toDeviceModel()
        } catch (e: Exception) {
            throw e
        }
    }
}
