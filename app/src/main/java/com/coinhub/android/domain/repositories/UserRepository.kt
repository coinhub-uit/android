package com.coinhub.android.domain.repositories

import com.coinhub.android.data.dtos.request.CreateDeviceRequestDto
import com.coinhub.android.data.dtos.request.CreateUserRequestDto
import com.coinhub.android.domain.models.DeviceModel
import com.coinhub.android.domain.models.NotificationModel
import com.coinhub.android.domain.models.SourceModel
import com.coinhub.android.domain.models.TicketModel
import com.coinhub.android.domain.models.UserModel

interface UserRepository {
    suspend fun getUserById(id: String, refresh: Boolean): UserModel?
    suspend fun registerProfile(user: CreateUserRequestDto): UserModel
    suspend fun updateProfile(id: String, user: CreateUserRequestDto)
    suspend fun delete(id: String)
    suspend fun getUserSources(id: String, refresh: Boolean): List<SourceModel>
    suspend fun getUserTickets(id: String, refresh: Boolean): List<TicketModel>
    suspend fun registerDevice(id: String, dto: CreateDeviceRequestDto): DeviceModel
    suspend fun getUserNotification(id: String, refresh: Boolean): List<NotificationModel>
}
