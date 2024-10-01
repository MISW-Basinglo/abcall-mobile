package co.uniandes.abcall.data.repositories.chat

import co.uniandes.abcall.data.models.Issue

interface ChatRepository {
    fun sendMessage(message: String): List<Issue>
}
