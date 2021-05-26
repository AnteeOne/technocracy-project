package com.anteeone.coverit.domain.models

data class MessageModel(
    val message: String,
    val senderId: String,
    val receiverId: String
){

    fun getChatId() =
        if(senderId > receiverId) "$senderId-$receiverId"
        else "$receiverId-$senderId"

    fun toMap() = hashMapOf(
        "messageId" to message,
        "receiverId" to receiverId,
        "senderId" to senderId
    )
}

