package com.orcalabs.hrms.data.model

data class ChatMessage(
    val id: String,
    val senderId: String,
    val senderName: String,
    val senderRole: String,
    val text: String,
    val timestamp: String = "10:45 AM",
    val isFromMe: Boolean = false,
    val channelId: String = "general" // "general", "sales-ts", "sales-ap", or direct user ID
)

data class ChatChannel(
    val id: String,
    val name: String,
    val description: String,
    val lastMessage: String,
    val lastMessageTime: String,
    val unreadCount: Int = 0,
    val isDirect: Boolean = false
)
