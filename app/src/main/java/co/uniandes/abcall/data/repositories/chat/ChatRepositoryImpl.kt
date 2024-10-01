package co.uniandes.abcall.data.repositories.chat

import co.uniandes.abcall.networking.AbcallApi
import co.uniandes.abcall.networking.MessageRequest
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val api: AbcallApi
): ChatRepository {

    override suspend fun sendMessage(message: String) {
        //api.sendMessage(MessageRequest(message))
    }

}
