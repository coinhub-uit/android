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
    data object Home : NavigationDestinations()

    @Serializable
    data object Vault : NavigationDestinations()

    @Serializable
    data object Profile : NavigationDestinations()

    @Serializable
    data object Notifications : NavigationDestinations()

    @Serializable
    data object AiChat : NavigationDestinations()

// --- //

    // Home
    @Serializable
    data object CreateSource : NavigationDestinations()

    @Serializable
    data object CreateTicket : NavigationDestinations()

    // Vault
    @Serializable
    data object EditSource : NavigationDestinations()

    @Serializable
    data object EditTicket : NavigationDestinations()

    // Profile
    @Serializable
    data object EditProfile : NavigationDestinations()

    @Serializable
    data object Settings : NavigationDestinations()
}