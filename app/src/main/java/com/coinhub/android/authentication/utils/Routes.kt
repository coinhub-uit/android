package com.coinhub.android.authentication.utils

import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object SignUp

//TODO: Define it later as dataclass to fetch data
@Serializable
data class Home(val id: String)
