package co.uniandes.abcall.data.repositories.chat

interface ChatRepository {
    suspend fun sendMessage(message: String)
}
