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

    // App - Main Graph - Home - Top Up Graph - Top Up
    @Serializable
    data object TopUp : AppNavDestinations()

    // App - Main Graph - Home - Top Up Graph - Top Up - Top Up Result
    @Serializable
    data object TopUpResult : AppNavDestinations()

    // App - Main Graph - Home - Source Detail
    @Serializable
    data class SourceDetail(
        val sourceId: String,
    ) : AppNavDestinations()

    // App - Main Graph - Home - Transfer Money Graph
    @Serializable
    data object TransferMoneyGraph : AppNavDestinations()

    // App - Main Graph - Home - Transfer Money Graph - Transfer Money
    @Serializable
    data object TransferMoney : AppNavDestinations()

    // App - Main Graph - Home - Transfer Money Graph - Transfer Money - Transfer Money Result
    @Serializable
    data object TransferMoneyResult : AppNavDestinations()

    // App - Main Graph - Tickets
    @Serializable
    data object Tickets : AppNavDestinations()

    // App - Main Graph - Tickets - Create Ticket Graph
    @Serializable
    data object CreateTicketGraph : AppNavDestinations()

    // App - Main Graph - Tickets - Create Ticket Graph - Create Ticket
    @Serializable
    data object CreateTicket : AppNavDestinations()

    // App - Main Graph - Tickets - Create Ticket Graph - Create Ticket - Confirm Create Ticket
    @Serializable
    data object ConfirmCreateTicket : AppNavDestinations()

    // App - Main Graph - Tickets - Create Ticket Graph - Ticket Details
    @Serializable
    data class TicketDetail(
        val ticketId: Int,
    ) : AppNavDestinations()

    // App - Main Graph - Menu
    @Serializable
    data object Menu : AppNavDestinations()

    // App - Main Graph - Menu - Edit Profile
    @Serializable
    data object EditProfile : AppNavDestinations()

    // App - Main Graph - Menu - Settings
    @Serializable
    data object Settings : AppNavDestinations()

    // App - Main Graph - Menu - Credential Change
    @Serializable
    data object CredentialChange : AppNavDestinations()

    // App - Main Graph - Notifications
    @Serializable
    data object Notification : AppNavDestinations()

    // App - Main Graph - AI Chat
    @Serializable
    data object AiChat : AppNavDestinations()
}
