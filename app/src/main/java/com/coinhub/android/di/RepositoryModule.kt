package com.coinhub.android.di

import com.coinhub.android.data.api_service.UserApiService
import com.coinhub.android.data.repository.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideAuthRepositoryImpl(supabaseClient: SupabaseClient, userApiService: UserApiService) =
        AuthRepositoryImpl(supabaseClient = supabaseClient, userApiService = userApiService)
}