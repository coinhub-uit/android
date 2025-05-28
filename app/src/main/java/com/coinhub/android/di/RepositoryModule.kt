package com.coinhub.android.di

import com.coinhub.android.data.api_service.UserApiService
import com.coinhub.android.data.remote.SupabaseService
import com.coinhub.android.data.repository.AuthRepositoryImpl
import com.coinhub.android.data.repository.SharedPreferenceRepositoryImpl
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
    fun provideSharedPreferencesRepository() = SharedPreferenceRepositoryImpl()
}