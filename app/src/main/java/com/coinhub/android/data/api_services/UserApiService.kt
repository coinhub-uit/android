package com.coinhub.android.data.api_services

import com.coinhub.android.data.dtos.request.CreateDeviceRequestDto
import com.coinhub.android.data.dtos.request.CreateUserRequestDto
import com.coinhub.android.data.dtos.response.DeviceResponseDto
import com.coinhub.android.data.dtos.response.NotificationResponseDto
import com.coinhub.android.data.dtos.response.SourceResponseDto
import com.coinhub.android.data.dtos.response.TicketResponseDto
import com.coinhub.android.data.dtos.response.UserResponseDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface UserApiService {
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") userId: String): UserResponseDto

    @POST("users")
    suspend fun registerProfile(@Body() user: CreateUserRequestDto): UserResponseDto

    @PUT("users/{id}")
    suspend fun updateProfile(
        @Path("id") userId: String,
        @Body user: CreateUserRequestDto,
    ): UserResponseDto

    @PATCH("users/{id}")
    suspend fun updatePartialProfile(
        @Path("id") userId: String,
        @Body user: CreateUserRequestDto,
    ): UserResponseDto

    @DELETE("users/{id}")
    suspend fun delete(@Path("id") userId: String): UserResponseDto

    @GET("users/{id}/sources")
    suspend fun getUserSources(@Path("id") userId: String): List<SourceResponseDto>

    @GET("users/{id}/tickets")
    suspend fun getUserTickets(@Path("id") userId: String): List<TicketResponseDto>

    // FIXME: @NTGNguyen change to dto
    @GET("users/{id}/notifications")
    suspend fun getUserNotification(@Path("id") userId: String): List<NotificationResponseDto>

    @POST("users/{id}/devices")
    suspend fun registerDevice(
        @Path("id") userId: String,
        @Body registerDeviceDto: CreateDeviceRequestDto,
    ): DeviceResponseDto

    @Multipart
    @POST("users/{id}/avatar")
    suspend fun uploadAvatar(
        @Path("id") userId: String,
        @Part avatar: MultipartBody.Part,
    ): UserResponseDto
}

