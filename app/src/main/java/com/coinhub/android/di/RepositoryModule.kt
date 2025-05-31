package com.coinhub.android.di

import com.coinhub.android.data.api_services.PaymentApiService
import com.coinhub.android.data.api_services.PlanApiService
import com.coinhub.android.data.api_services.SourceApiService
import com.coinhub.android.data.api_services.TicketApiService
import com.coinhub.android.data.api_services.TopUpApiService
import com.coinhub.android.data.api_services.UserApiService
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.data.repositories.AuthRepositoryImpl
import com.coinhub.android.data.repositories.PaymentRepositoryImpl
import com.coinhub.android.data.repositories.PlanRepositoryImpl
import com.coinhub.android.data.repositories.SharedPreferenceRepositoryImpl
import com.coinhub.android.data.repositories.SourceRepositoryImpl
import com.coinhub.android.data.repositories.TicketRepositoryImpl
import com.coinhub.android.data.repositories.TopUpRepositoryImpl
import com.coinhub.android.data.repositories.UserRepositoryImpl
import com.coinhub.android.domain.repositories.PaymentRepository
import com.coinhub.android.domain.repositories.PlanRepository
import com.coinhub.android.domain.repositories.SourceRepository
import com.coinhub.android.domain.repositories.TicketRepository
import com.coinhub.android.domain.repositories.TopUpRepository
import com.coinhub.android.domain.repositories.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideAuthRepositoryImpl(supabaseService: SupabaseService, userApiService: UserApiService) =
        AuthRepositoryImpl(supabaseService = supabaseService, userApiService = userApiService)

    @Singleton
    @Provides
    fun provideUserRepository(userApiService: UserApiService): UserRepository =
        UserRepositoryImpl(userApiService)

    @Singleton
    @Provides
    fun provideSourceRepository(sourceApiService: SourceApiService): SourceRepository =
        SourceRepositoryImpl(sourceApiService)

    @Singleton
    @Provides
    fun provideTopUpRepository(topUpApiService: TopUpApiService): TopUpRepository = TopUpRepositoryImpl(topUpApiService)

    @Provides
    fun provideTicketRepository(ticketApiService: TicketApiService): TicketRepository =
        TicketRepositoryImpl(ticketApiService)

    @Singleton
    @Provides
    fun providePlanRepository(planApiService: PlanApiService): PlanRepository =
        PlanRepositoryImpl(planApiService)

    @Singleton
    @Provides
    fun providePaymentRepository(paymentApiService: PaymentApiService): PaymentRepository =
        PaymentRepositoryImpl(paymentApiService)

    @Singleton
    @Provides
    fun provideSharedPreferencesRepository() = SharedPreferenceRepositoryImpl()
}