package com.coinhub.android.data.repositories

import android.util.Log
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

    override suspend fun getUserById(id: String): UserModel? {
        return try {
            userApiService.getUserById(id)?.toUserModel()
        } catch (e: Exception) {
            throw e
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

    override suspend fun getUserSources(id: String): List<SourceModel> {
        return try {
            userApiService.getUserSources(id).map {
                it.toSourceModel()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getUserTickets(id: String): List<TicketModel> {
        return try {
            userApiService.getUserTickets(id).map {
                it.toTicketModel()
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun registerDevice(id: String, dto: CreateDeviceRequestDto): DeviceModel {
        return try {
            userApiService.registerDevice(id, dto).toDeviceModel()
        } catch (e: Exception) {
            throw e
        }
    }
}
