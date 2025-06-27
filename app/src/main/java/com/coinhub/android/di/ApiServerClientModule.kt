package com.coinhub.android.di

import com.coinhub.android.BuildConfig
import com.coinhub.android.data.api_services.AiChatApiService
import com.coinhub.android.data.api_services.NotificationApiService
import com.coinhub.android.data.api_services.PaymentApiService
import com.coinhub.android.data.api_services.PlanApiService
import com.coinhub.android.data.api_services.SettingApiService
import com.coinhub.android.data.api_services.SourceApiService
import com.coinhub.android.data.api_services.TicketApiService
import com.coinhub.android.data.api_services.UserApiService
import com.coinhub.android.data.remote.SupabaseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServerClientModule {
    private const val API_URL = BuildConfig.apiServerUrl

    @Singleton
    @Provides
    fun provideHttpClient(supabaseService: SupabaseService): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }

        return OkHttpClient.Builder()
            .callTimeout(Duration.ZERO) // FIXME: Too lazy to handle timeout exceptions
            .connectTimeout(Duration.ZERO)
            .readTimeout(Duration.ZERO)
            .writeTimeout(Duration.ZERO)
            .addInterceptor { chain ->
                val token = supabaseService.getToken().orEmpty()
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(loggingInterceptor)
            .build()
    }

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

    @Singleton
    @Provides
    fun provideSettingApiService(retrofit: Retrofit): SettingApiService =
        retrofit.create(SettingApiService::class.java)
}
