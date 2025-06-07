package com.coinhub.android.di

import com.coinhub.android.domain.managers.ThemeManger
import com.coinhub.android.domain.managers.UserManager
import com.coinhub.android.domain.repositories.PreferenceDataStore
import com.coinhub.android.domain.repositories.UserRepository
import com.coinhub.android.domain.use_cases.CheckProfileAvailableUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonManagerModule {


    @Provides
    @Singleton
    fun provideThemeManager(preferenceDataStore: PreferenceDataStore, @IoDispatcher ioDispatcher: CoroutineDispatcher) =
        ThemeManger(preferenceDataStore, ioDispatcher = ioDispatcher)
}
