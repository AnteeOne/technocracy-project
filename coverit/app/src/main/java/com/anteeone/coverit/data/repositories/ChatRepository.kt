package com.anteeone.coverit.data.repositories

import com.anteeone.coverit.data.dto.MessagesDto
import com.anteeone.coverit.domain.models.MessageModel
import com.anteeone.coverit.domain.repositories.IChatRepository
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.Exception

class ChatRepository @Inject constructor(
    private val firebaseDatabase: FirebaseFirestore
) : IChatRepository {

    private val messagesCollection = firebaseDatabase.collection("messages")

    override suspend fun getAllMessages(senderId: String, receiverId: String): List<MessageModel> = suspendCancellableCoroutine { cor ->
        try{
            messagesCollection
                .document(MessageModel("",senderId, receiverId).getChatId())
                .get()
                .addOnSuccessListener {
                    try{
                        val dto = MessagesDto(it["messages"] as List<Map<String, String>>)
                        cor.resumeWith(Result.success(dto.toMessagesList()))
                    }
                    catch (ex: Exception){
                        cor.resumeWith(Result.success(emptyList()))
                    }
                }
                .addOnFailureListener {
                    cor.resumeWith(Result.failure(it))
                }
        }
        catch (ex:Exception){
            cor.resumeWith(Result.failure(ex))
        }
    }

    override suspend fun addMessage(message: MessageModel): Unit = suspendCancellableCoroutine { cor ->
        try {
            messagesCollection
                .document(message.getChatId())
                .update("messages",FieldValue.arrayUnion(message.toMap()))
                .addOnSuccessListener {
                    cor.resumeWith(Result.success(Unit))
                }
                .addOnFailureListener {
                    cor.resumeWith(Result.failure(it))
                }
        }
        catch (ex: Exception){
            cor.resumeWith(Result.failure(ex))
        }
    }

    override suspend fun createChat(senderId: String, receiverId: String): Unit = suspendCancellableCoroutine { cor ->
        try {
            messagesCollection
                .document(MessageModel("",senderId, receiverId).getChatId())
                .set(MessagesDto(emptyList()))
                .addOnSuccessListener {
                    cor.resumeWith(Result.success(Unit))
                }
                .addOnFailureListener {
                    cor.resumeWith(Result.failure(it))
                }
        }
        catch (ex: Exception){
            cor.resumeWith(Result.failure(ex))
        }
    }
}