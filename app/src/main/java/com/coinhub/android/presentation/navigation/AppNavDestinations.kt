package com.coinhub.android.presentation.navigation

import kotlinx.serialization.Serializable

open class AppNavDestinations {
    // Auth
    @Serializable
    data object Auth : AppNavDestinations()

    @Serializable
    data object ConfirmAccount : AppNavDestinations()

    // Auth - Create Profile
    @Serializable
    data object CreateProfile : AppNavDestinations()

    // ------- //

    // App - Main Graph
    @Serializable
    data object MainGraph : AppNavDestinations()

    // App - Main Graph - Home
    @Serializable
    data object Home : AppNavDestinations()

    // App - Main Graph - Home - Create Source Graph
    @Serializable
    data object CreateSourceGraph : AppNavDestinations()

    // App - Main Graph - Home - Create Source
    @Serializable
    data object CreateSource : AppNavDestinations()

    // App - Main Graph - Home - Top Up Graph
    @Serializable
    data object TopUpGraph : AppNavDestinations()

    // App - Main Graph - Home - Top Up
    @Serializable
    data object TopUp : AppNavDestinations()

    // App - Main Graph - Home - Top Up - Top Up Result
    @Serializable
    data object TopUpResult : AppNavDestinations()

    // App - Main Graph - Home - Source Detail
    @Serializable
    data object SourceDetail : AppNavDestinations()

    // App - Main Graph - Vault
    @Serializable
    data object Vault : AppNavDestinations()

    // App - Main Graph - Vault - Create Ticket Graph
    @Serializable
    data object CreateTicketGraph : AppNavDestinations()

    // App - Main Graph - Vault - Create Ticket
    @Serializable
    data object CreateTicket : AppNavDestinations()

    // App - Main Graph - Vault - Create Ticket - Confirm Create Ticket
    @Serializable
    data object ConfirmCreateTicket : AppNavDestinations()

    // App - Main Graph - Vault - Ticket Details
    @Serializable
    data object TicketDetail : AppNavDestinations()

    // App - Main Graph - Menu
    @Serializable
    data object Menu : AppNavDestinations()

    // App - Main Graph - Menu - Edit Profile
    @Serializable
    data object EditProfile : AppNavDestinations()

    // App - Main Graph - Menu - Settings
    @Serializable
    data object Settings : AppNavDestinations()

    // App - Main Graph - Notifications
    @Serializable
    data object Notification : AppNavDestinations()

    // App - Main Graph - AI Chat
    @Serializable
    data object AiChat : AppNavDestinations()
}
