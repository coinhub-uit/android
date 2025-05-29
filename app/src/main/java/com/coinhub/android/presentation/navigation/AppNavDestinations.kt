package com.coinhub.android.presentation.navigation

import kotlinx.serialization.Serializable

open class AppNavDestinations {
    // Auth
    @Serializable
    data object Auth : AppNavDestinations()

    @Serializable
    data object ConfirmAccount : AppNavDestinations()

    @Serializable
    data object CreateProfile : AppNavDestinations()

    // Main
    @Serializable
    data object Main : AppNavDestinations()

    @Serializable
    data object Home : AppNavDestinations()

    @Serializable
    data object Profile : AppNavDestinations()

    @Serializable
    data object Vault : AppNavDestinations()

    @Serializable
    data object EditSource : AppNavDestinations()

    @Serializable
    data object EditTicket : AppNavDestinations()

    @Serializable
    data object EditProfile : AppNavDestinations()

    @Serializable
    data object Settings : AppNavDestinations()

    @Serializable
    data object Notification : AppNavDestinations()

    @Serializable
    data object AiChat : AppNavDestinations()

// --- //

    // Create Source
    @Serializable
    data object CreateSourceGraph : AppNavDestinations()

    @Serializable
    data object CreateSource : AppNavDestinations()

    // Top Up
    @Serializable
    data object TopUpGraph : AppNavDestinations()

    @Serializable
    data object TopUp : AppNavDestinations()

    @Serializable
    data object TopUpResult : AppNavDestinations()

    // Create Ticket
    @Serializable
    data object CreateTicketGraph : AppNavDestinations()

    @Serializable
    data object CreateTicket : AppNavDestinations()

    @Serializable
    data object ConfirmCreateTicket : AppNavDestinations()
}
