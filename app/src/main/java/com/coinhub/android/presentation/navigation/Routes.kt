package com.coinhub.android.presentation.navigation

import kotlinx.serialization.Serializable

sealed class NavigationDestinations {
    // Auth
    @Serializable
    data object AuthGraph : NavigationDestinations()

    @Serializable
    data object Auth : NavigationDestinations()

    @Serializable
    data object ConfirmAccount : NavigationDestinations()

    @Serializable
    data object CreateProfile : NavigationDestinations()

    // Main
    @Serializable
    data object MainGraph : NavigationDestinations()

    @Serializable
    data object Main : NavigationDestinations()

    @Serializable
    data object Home : NavigationDestinations()

    @Serializable
    data object Vault : NavigationDestinations()

    @Serializable
    data object Profile : NavigationDestinations()

    @Serializable
    data object Notification : NavigationDestinations()

    @Serializable
    data object AiChat : NavigationDestinations()

// --- //

    // Create Source
    @Serializable
    data object CreateSourceGraph : NavigationDestinations()

    @Serializable
    data object CreateSource : NavigationDestinations()

    // Top Up
    @Serializable
    data object TopUpGraph : NavigationDestinations()

    @Serializable
    data object TopUp : NavigationDestinations()

    @Serializable
    data object TopUpResult : NavigationDestinations()

    // Create Ticket
    @Serializable
    data object CreateTicketGraph : NavigationDestinations()

    @Serializable
    data object CreateTicket : NavigationDestinations()

    @Serializable
    data object ConfirmCreateTicket : NavigationDestinations()

    // Vault
    @Serializable
    data object VaultGraph : NavigationDestinations()

    @Serializable
    data object EditSource : NavigationDestinations()

    @Serializable
    data object EditTicket : NavigationDestinations()

    // Profile
    @Serializable
    data object ProfileGraph : NavigationDestinations()

    @Serializable
    data object EditProfile : NavigationDestinations()

    @Serializable
    data object Settings : NavigationDestinations()
}
