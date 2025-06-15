package com.coinhub.android.di

import android.content.Context
import com.coinhub.android.utils.DeviceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {
    @Provides
    @Singleton
    fun provideDeviceHelper(@ApplicationContext context: Context) = DeviceHelper(context)
}