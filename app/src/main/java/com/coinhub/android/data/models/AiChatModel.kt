package com.coinhub.android.data.models

data class AiChatModel(
    val role: Role,
    val message: String,
) {
    enum class Role {
        USER, SYSTEM, ASSISTANT
    }
}
