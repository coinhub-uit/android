package com.coinhub.android.data.api_services

import com.coinhub.android.data.dtos.request.CreateUserDto
import com.coinhub.android.data.dtos.request.RegisterDeviceDto
import com.coinhub.android.data.dtos.response.DeviceDto
import com.coinhub.android.data.dtos.response.SourceDto
import com.coinhub.android.data.dtos.response.TicketDto
import com.coinhub.android.data.dtos.response.UserDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApiService {
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") userId: String): UserDto?

    @POST("users")
    suspend fun registerProfile(@Body() user: CreateUserDto): UserDto

    @PUT("users/{id}")
    suspend fun updateProfile(@Path("id") userId: String, @Body user: CreateUserDto): UserDto

    @PATCH("users/{id}")
    suspend fun updatePartialProfile(@Path("id") userId: String, @Body user: CreateUserDto): UserDto

    @DELETE("users/{id}")
    suspend fun delete(@Path("id") userId: String): UserDto

    @GET("users/{id}/sources")
    suspend fun getUserSources(@Path("id") userId: String): List<SourceDto>

    @GET("users/{id}/tickets")
    suspend fun getUserTickets(@Path("id") userId: String): List<TicketDto>

    @POST("users/{id}/devices")
    suspend fun registerDevice(@Path("id") userId: String, @Body registerDeviceDto: RegisterDeviceDto): DeviceDto
}
