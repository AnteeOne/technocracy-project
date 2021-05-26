package com.anteeone.coverit.domain.repositories

import com.anteeone.coverit.domain.models.MessageModel

interface IChatRepository {

    suspend fun getAllMessages(senderId: String, receiverId: String): List<MessageModel>

    suspend fun addMessage(message: MessageModel): Unit;

    suspend fun createChat(senderId: String,receiverId: String): Unit

}