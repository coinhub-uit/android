package com.coinhub.android.authentication.data.network

import com.coinhub.android.authentication.data.api.AuthApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiServerClient {
    private var token: String = ""
    private const val apiUrl = ""

    fun setToken(newToken: String) {
        token = newToken
    }

    fun unsetToken() {
        token = ""
    }

    private val okHttpClient: OkHttpClient
        get() {
            return OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                    chain.proceed(request)
                }
                .build()
        }

    val apiService: AuthApiService by lazy {
        Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(AuthApiService::class.java)
    }
}
