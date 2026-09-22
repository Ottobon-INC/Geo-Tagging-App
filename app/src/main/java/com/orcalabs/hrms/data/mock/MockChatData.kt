package com.orcalabs.hrms.data.mock

import com.orcalabs.hrms.data.model.ChatChannel
import com.orcalabs.hrms.data.model.ChatMessage

object MockChatData {

    val channels: List<ChatChannel> = listOf(
        ChatChannel(
            id = "general",
            name = "Orca Labs Announcements",
            description = "Company-wide updates from Management & HR",
            lastMessage = "Q3 Incentive scheme details released. Please check notice board.",
            lastMessageTime = "10:30 AM",
            unreadCount = 1
        ),
        ChatChannel(
            id = "sales-ts",
            name = "Telangana Sales Force",
            description = "Daily coordination for Hyderabad & Telangana territories",
            lastMessage = "Murali Krishna: Team, ensure morning call reporting is done by 1:30 PM.",
            lastMessageTime = "11:45 AM",
            unreadCount = 2
        ),
        ChatChannel(
            id = "sales-ap",
            name = "Andhra Pradesh Sales Force",
            description = "Coastal AP & Rayalaseema sales coordination",
            lastMessage = "B V Janardhan: Good job on the institutional order in Guntur.",
            lastMessageTime = "Yesterday",
            unreadCount = 0
        )
    )

    val initialMessages: MutableList<ChatMessage> = mutableListOf(
        ChatMessage(
            id = "M-1",
            senderId = "OL009",
            senderName = "N V Divya Sirisha",
            senderRole = "HR Manager",
            text = "Good morning all! Please ensure all pending leave requests for October are submitted by Friday.",
            timestamp = "09:00 AM",
            isFromMe = false,
            channelId = "general"
        ),
        ChatMessage(
            id = "M-2",
            senderId = "OL026",
            senderName = "P Aswani",
            senderRole = "General Manager",
            text = "Congratulations to the Telangana team for crossing 85% of monthly target by the 20th!",
            timestamp = "09:30 AM",
            isFromMe = false,
            channelId = "general"
        ),
        ChatMessage(
            id = "M-3",
            senderId = "OL005",
            senderName = "Murali Krishna K",
            senderRole = "RSM Hyderabad",
            text = "Team, focus on Orca-Cef 200 today. Doctors are giving positive feedback on the new blister pack.",
            timestamp = "10:15 AM",
            isFromMe = false,
            channelId = "sales-ts"
        ),
        ChatMessage(
            id = "M-4",
            senderId = "OL010",
            senderName = "Sandeep Reddy G",
            senderRole = "BE Banjara Hills",
            text = "Sir, already completed 2 doctor visits and booked 28.5k POB at Balaji Medicals.",
            timestamp = "11:15 AM",
            isFromMe = true,
            channelId = "sales-ts"
        )
    )
}
