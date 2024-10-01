package co.uniandes.abcall.data.repositories.chat

import co.uniandes.abcall.data.models.Issue
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(): ChatRepository {

    override fun sendMessage(message: String): List<Issue> {
        TODO("Not yet implemented")
    }

}