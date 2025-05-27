package com.coinhub.android.di

import com.coinhub.android.data.remote.SupabaseService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupabaseServiceModule {
    @Singleton
    @Provides
    fun provideSupabaseService(supabaseClient: SupabaseClient) = SupabaseService(supabaseClient)
}
