package com.coinhub.android.navigation

import com.coinhub.android.data.models.User
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
object Auth

@Serializable
data class Home(@Contextual val user: User)
