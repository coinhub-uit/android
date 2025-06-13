package com.coinhub.android.di

import com.coinhub.android.domain.managers.ThemeManger

import com.coinhub.android.domain.repositories.PreferenceDataStore
import com.coinhub.android.domain.managers.LockHashingManager

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonManagerModule {

    @Provides
    @Singleton
    fun provideThemeManager(preferenceDataStore: PreferenceDataStore, @IoDispatcher ioDispatcher: CoroutineDispatcher) =
        ThemeManger(preferenceDataStore, ioDispatcher = ioDispatcher)

    @Provides
    @ViewModelScoped
    fun provideLockHashingManager(preferenceDataStore: PreferenceDataStore) = LockHashingManager(preferenceDataStore)
}
