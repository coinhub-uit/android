package com.coinhub.android.domain.repositories

import com.coinhub.android.data.dtos.request.CreateDeviceRequestDto
import com.coinhub.android.data.dtos.request.CreateUserRequestDto
import com.coinhub.android.data.models.DeviceModel
import com.coinhub.android.data.models.SourceModel
import com.coinhub.android.data.models.TicketModel
import com.coinhub.android.data.models.UserModel

interface UserRepository {
    suspend fun getUserById(id: String): UserModel
    suspend fun registerProfile(user: CreateUserRequestDto): UserModel
    suspend fun updateProfile(id: String, user: CreateUserRequestDto)
    suspend fun updatePartialProfile(id: String, user: CreateUserRequestDto): UserModel
    suspend fun delete(id: String): UserModel
    suspend fun getUserSources(id: String): List<SourceModel>
    suspend fun getUserTickets(id: String): List<TicketModel>
    suspend fun registerDevice(id: String, dto: CreateDeviceRequestDto): DeviceModel
}
