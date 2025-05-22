package com.coinhub.android.authentication.data.api

import com.coinhub.android.authentication.data.dtos.CreateUserDto
import com.coinhub.android.authentication.data.model.User
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthApiService {
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") userId: String): User?

    @POST("users")
    suspend fun registerProfile(@Body() user: CreateUserDto): User
}
