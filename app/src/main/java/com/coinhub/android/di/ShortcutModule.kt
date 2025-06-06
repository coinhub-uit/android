package com.coinhub.android.di

import android.content.Context
import com.coinhub.android.shortcuts.TicketScreenShortcut
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ShortcutModule {
    @Provides
    fun provideTicketScreenShortcut(
        @ApplicationContext context: Context
    ): TicketScreenShortcut {
        return TicketScreenShortcut(
            context = context
        )
    }
}