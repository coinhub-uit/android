package com.coinhub.android.domain.models

data class AiChatModel(
    val role: Role,
    val content: String,
) {
    enum class Role {
        USER, SYSTEM, ASSISTANT;

        override fun toString(): String {
            return name.lowercase()
        }
    }
}
