package com.coinhub.android.di

import com.coinhub.android.BuildConfig
import com.coinhub.android.data.api_services.UserApiService
import com.coinhub.android.data.repositories.SharedPreferenceRepositoryImpl
import com.coinhub.android.utils.ACCESS_TOKEN_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServerClientModule {
    private const val API_URL = BuildConfig.apiServerUrl

    @Singleton
    @Provides
    fun provideHttpClient(sharedPreferenceRepositoryImpl: SharedPreferenceRepositoryImpl) = OkHttpClient.Builder()
        .addInterceptor { chain ->
            var token = sharedPreferenceRepositoryImpl.getStringData(ACCESS_TOKEN_KEY)
            if (token.isNullOrEmpty()) {
                token = ""
            }
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token") // FIXME: token here
                .build()
            chain.proceed(request)
        }
        .build()

    // TODO: https://freedium.cfd/https://medium.com/@ramadan123sayed/retrofit-with-hilt-in-kotlin-f1046ae9b2be
    @Singleton
    @Provides
    fun provideUserApiService(okHttpClient: OkHttpClient): UserApiService = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(UserApiService::class.java)
}
