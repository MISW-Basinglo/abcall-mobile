package co.uniandes.abcall.ui.screens.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.uniandes.abcall.data.models.Issue
import co.uniandes.abcall.data.models.Message
import co.uniandes.abcall.data.repositories.chat.ChatRepository
import co.uniandes.abcall.data.repositories.issues.IssuesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val repository: ChatRepository
) : ViewModel() {
    private val _messages = MutableLiveData<List<Message>>(emptyList())
    val messages: LiveData<List<Message>> get() = _messages

    fun sendMessage(text: String) {
        repository.sendMessage(text)
        val currentMessages = _messages.value?.toMutableList() ?: mutableListOf()
        currentMessages.add(0, Message(text = text, isUser = true))
        _messages.value = currentMessages
        currentMessages.add(0, Message(text = "Respuesta automática: ${currentMessages.size}", isUser = false))
        _messages.value = currentMessages
    }

}
