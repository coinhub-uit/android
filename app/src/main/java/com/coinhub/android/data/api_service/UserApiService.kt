package com.coinhub.android.data.api_service

import com.coinhub.android.data.dtos.CreateUserDto
import com.coinhub.android.data.models.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApiService {
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") userId: String): User?

    @POST("users")
    suspend fun registerProfile(@Body() user: CreateUserDto): User
}
