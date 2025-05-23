package com.coinhub.android.navigation

import com.coinhub.android.data.models.User
import kotlinx.serialization.Serializable

@Serializable
object SignIn

@Serializable
object SignUp

@Serializable
data class Home(val user: User)
