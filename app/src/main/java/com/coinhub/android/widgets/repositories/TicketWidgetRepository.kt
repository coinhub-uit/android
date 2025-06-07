package com.coinhub.android.widgets.repositories

import android.content.Context
import com.coinhub.android.domain.repositories.PreferenceDataStore
import dagger.hilt.EntryPoint
import dagger.hilt.EntryPoints
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import java.math.BigInteger
import javax.inject.Inject

class TicketWidgetRepository @Inject constructor(
    private val preferenceDataStore: PreferenceDataStore,
) {
    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface TicketWidgetRepositoryEntryPoint {
        fun ticketWidgetRepository(): TicketWidgetRepository
    }

    companion object {
        fun getInstance(context: Context): TicketWidgetRepository {
            val entryPoint = EntryPoints.get(
                context,
                TicketWidgetRepositoryEntryPoint::class.java
            )
            return entryPoint.ticketWidgetRepository()
        }
    }

    suspend fun getTotalPrincipal(): Flow<BigInteger?> {
        return preferenceDataStore.getTotalPrincipal()
    }

    suspend fun getTotalInterest(): Flow<BigInteger?> {
        return preferenceDataStore.getTotalInterest()
    }
}