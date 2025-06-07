package com.coinhub.android.domain.models

data class AiChatModel(
    val role: Role,
    val message: String,
) {
    enum class Role {
        USER, SYSTEM, ASSISTANT
    }
}
