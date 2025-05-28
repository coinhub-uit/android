package com.coinhub.android.data.api_service

import com.coinhub.android.data.dtos.CreateUserDto
import com.coinhub.android.data.dtos.RegisterDeviceDto
import com.coinhub.android.data.models.Device
import com.coinhub.android.data.models.Source
import com.coinhub.android.data.models.Ticket
import com.coinhub.android.data.models.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApiService {
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") userId: String): User?

    @POST("users")
    suspend fun registerProfile(@Body() user: CreateUserDto): User

    @PUT("users/{id}")
    suspend fun updateProfile(@Path("id") userId: String, @Body user: CreateUserDto)

    @PATCH("users/{id}")
    suspend fun updatePartialProfile(@Path("id") userId: String, @Body user: CreateUserDto): User

    @DELETE("users/{id}")
    suspend fun delete(@Path("id") userId: String): User

    @GET("users/{id}/sources")
    suspend fun getUserSources(@Path("id") userId: String): List<Source>

    @GET("users/{id}/tickets")
    suspend fun getUserTickets(@Path("id") userId: String): List<Ticket>

    @POST("users/{id}/devices")
    suspend fun registerDevice(@Path("id") userId: String, @Body registerDeviceDto: RegisterDeviceDto): Device
}
