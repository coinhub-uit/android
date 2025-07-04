package com.coinhub.android.di

import com.coinhub.android.data.repositories.AiChatRepositoryImpl
import com.coinhub.android.data.repositories.AuthRepositoryImpl
import com.coinhub.android.data.repositories.PaymentRepositoryImpl
import com.coinhub.android.data.repositories.PlanRepositoryImpl
import com.coinhub.android.data.repositories.PreferenceDataStoreImpl
import com.coinhub.android.data.repositories.SettingRepositoryImpl
import com.coinhub.android.data.repositories.SourceRepositoryImpl
import com.coinhub.android.data.repositories.TicketRepositoryImpl
import com.coinhub.android.domain.repositories.AiChatRepository
import com.coinhub.android.domain.repositories.AuthRepository
import com.coinhub.android.domain.repositories.PaymentRepository
import com.coinhub.android.domain.repositories.PlanRepository
import com.coinhub.android.domain.repositories.PreferenceDataStore
import com.coinhub.android.domain.repositories.SettingRepository
import com.coinhub.android.domain.repositories.SourceRepository
import com.coinhub.android.domain.repositories.TicketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideAuthRepositoryImpl(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideSourceRepository(impl: SourceRepositoryImpl): SourceRepository

    @Binds
    abstract fun provideTicketRepository(impl: TicketRepositoryImpl): TicketRepository

    @Binds
    abstract fun providePlanRepository(impl: PlanRepositoryImpl): PlanRepository

    @Binds
    abstract fun providePaymentRepository(impl: PaymentRepositoryImpl): PaymentRepository

    @Binds
    abstract fun provideAiChatRepository(impl: AiChatRepositoryImpl): AiChatRepository

    @Binds
    abstract fun provideSharedPreferencesRepository(impl: PreferenceDataStoreImpl): PreferenceDataStore

    @Binds
    abstract fun provideSettingRepository(impl: SettingRepositoryImpl): SettingRepository
}