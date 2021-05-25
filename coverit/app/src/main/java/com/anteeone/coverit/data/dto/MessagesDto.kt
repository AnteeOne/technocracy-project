package com.anteeone.coverit.data.dto

import com.anteeone.coverit.domain.models.MessageModel

data class MessagesDto(
    val messages:List<Map<String,String>>
){
    fun toMessagesList(): List<MessageModel>{
        val list = mutableListOf<MessageModel>()
        for (msgMap in messages){
            list.add(
                MessageModel(
                    message = msgMap["messageId"]!!,
                    senderId = msgMap["senderId"]!!,
                    receiverId = msgMap["receiverId"]!!
                )
            )
        }
        return list
    }
}