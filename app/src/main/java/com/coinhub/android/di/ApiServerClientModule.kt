package com.coinhub.android.di

import com.coinhub.android.BuildConfig
import com.coinhub.android.data.api_services.AiChatApiService
import com.coinhub.android.data.api_services.NotificationApiService
import com.coinhub.android.data.api_services.PaymentApiService
import com.coinhub.android.data.api_services.PlanApiService
import com.coinhub.android.data.api_services.SourceApiService
import com.coinhub.android.data.api_services.TicketApiService
import com.coinhub.android.data.api_services.UserApiService
import com.coinhub.android.data.remote.SupabaseService
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
    fun provideHttpClient(supabaseService: SupabaseService) = OkHttpClient.Builder().addInterceptor { chain ->
            var token = supabaseService.getToken()
            if (token.isNullOrEmpty()) {
                token = ""
            }
            val request = chain.request().newBuilder().addHeader("Authorization", "Bearer $token") // FIXME: token here
                .build()
            chain.proceed(request)
        }.build()

    // TODO: https://freedium.cfd/https://medium.com/@ramadan123sayed/retrofit-with-hilt-in-kotlin-f1046ae9b2be
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideUserApiService(retrofit: Retrofit): UserApiService = retrofit.create(UserApiService::class.java)

    @Singleton
    @Provides
    fun provideSourceApiService(retrofit: Retrofit): SourceApiService = retrofit.create(SourceApiService::class.java)

    @Singleton
    @Provides
    fun provideTicketApiService(retrofit: Retrofit): TicketApiService = retrofit.create(TicketApiService::class.java)

    @Singleton
    @Provides
    fun providePlanApiService(retrofit: Retrofit): PlanApiService = retrofit.create(PlanApiService::class.java)

    @Singleton
    @Provides
    fun providePaymentApiService(retrofit: Retrofit): PaymentApiService = retrofit.create(PaymentApiService::class.java)

    @Singleton
    @Provides
    fun provideAiChatApiService(retrofit: Retrofit): AiChatApiService = retrofit.create(AiChatApiService::class.java)

    @Singleton
    @Provides
    fun provideNotificationApiService(retrofit: Retrofit): NotificationApiService =
        retrofit.create(NotificationApiService::class.java)
}
