package com.orcalabs.hrms.ui.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orcalabs.hrms.data.model.ChatChannel
import com.orcalabs.hrms.data.model.ChatMessage
import com.orcalabs.hrms.domain.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ChatUiState(
    val channels: List<ChatChannel> = emptyList(),
    val selectedChannelId: String = "sales-ts",
    val messages: List<ChatMessage> = emptyList(),
    val currentInputText: String = ""
)

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            chatRepository.channels.collect { chList ->
                _uiState.value = _uiState.value.copy(channels = chList)
            }
        }
        observeMessages("sales-ts")
    }

    fun selectChannel(channelId: String) {
        _uiState.value = _uiState.value.copy(selectedChannelId = channelId)
        observeMessages(channelId)
    }

    private fun observeMessages(channelId: String) {
        viewModelScope.launch {
            chatRepository.getMessages(channelId).collect { msgList ->
                _uiState.value = _uiState.value.copy(messages = msgList)
            }
        }
    }

    fun onInputTextChange(text: String) {
        _uiState.value = _uiState.value.copy(currentInputText = text)
    }

    fun sendMessage() {
        val text = _uiState.value.currentInputText.trim()
        if (text.isBlank()) return
        val chId = _uiState.value.selectedChannelId

        viewModelScope.launch {
            chatRepository.sendMessage(chId, text)
            _uiState.value = _uiState.value.copy(currentInputText = "")
        }
    }
}
