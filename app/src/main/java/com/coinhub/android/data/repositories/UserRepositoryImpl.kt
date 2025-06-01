package com.coinhub.android.data.repositories

import com.coinhub.android.data.api_services.UserApiService
import com.coinhub.android.data.dtos.CreateUserDto
import com.coinhub.android.data.dtos.RegisterDeviceDto
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
            userApiService.getUserById(id)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun registerProfile(user: CreateUserDto): UserModel {
        return try {
            userApiService.registerProfile(user)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateProfile(id: String, user: CreateUserDto) {
        try {
            userApiService.updateProfile(id, user)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updatePartialProfile(id: String, user: CreateUserDto): UserModel {
        return try {
            userApiService.updatePartialProfile(id, user)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun delete(id: String): UserModel {
        return try {
            userApiService.delete(id)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getUserSources(id: String): List<SourceModel> {
        return try {
            userApiService.getUserSources(id)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getUserTickets(id: String): List<TicketModel> {
        return try {
            userApiService.getUserTickets(id)
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun registerDevice(id: String, dto: RegisterDeviceDto): DeviceModel {
        return try {
            userApiService.registerDevice(id, dto)
        } catch (e: Exception) {
            throw e
        }
    }
}
