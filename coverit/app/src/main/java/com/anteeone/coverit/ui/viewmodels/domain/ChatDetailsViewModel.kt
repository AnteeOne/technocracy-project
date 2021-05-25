package com.anteeone.coverit.ui.viewmodels.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anteeone.coverit.domain.models.MessageModel
import com.anteeone.coverit.domain.usecases.chat.AddMessageUsecase
import com.anteeone.coverit.domain.usecases.chat.GetMessagesUsecase
import com.anteeone.coverit.domain.utils.Outcome
import com.anteeone.coverit.ui.utils.extensions._log
import javax.inject.Inject

class ChatDetailsViewModel @Inject constructor(
    private val getMessagesUsecase: GetMessagesUsecase,
    private val addMessageUsecase: AddMessageUsecase
) : ViewModel() {

    sealed class MessagesState {
        object Empty : MessagesState()
        object Failure : MessagesState()
        data class Success(val data: List<MessageModel>) : MessagesState()
    }

    val messagesStateLiveData: MutableLiveData<MessagesState> =
        MutableLiveData(MessagesState.Empty)

    fun loadMessages(receiverId: String) {
        getMessagesUsecase(viewModelScope, GetMessagesUsecase.Params(receiverId)) {
            when (it) {
                is Outcome.Failure -> {
                    messagesStateLiveData.postValue(MessagesState.Failure)
                }
                is Outcome.Success -> {
                    messagesStateLiveData.postValue(MessagesState.Success(it.data))
                }
            }
        }
    }

    fun addMessage(message: String, receiverId: String) {
        addMessageUsecase(viewModelScope, AddMessageUsecase.Params(message, receiverId)) {
            when (it) {
                is Outcome.Failure -> {
                    //todo: handle message sending error
                    loadMessages(receiverId)
                }
                is Outcome.Success -> {
                    loadMessages(receiverId)
                }
            }
        }
    }

}