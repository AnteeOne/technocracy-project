package com.anteeone.coverit.ui.viewmodels.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anteeone.coverit.domain.models.MessageModel
import com.anteeone.coverit.domain.usecases.chat.AddMessageUsecase
import com.anteeone.coverit.domain.usecases.chat.GetMessagesUsecase
import com.anteeone.coverit.domain.usecases.domain.GetUserUsecase
import com.anteeone.coverit.domain.utils.Outcome
import com.anteeone.coverit.ui.utils.extensions._log
import javax.inject.Inject

class ChatDetailsViewModel @Inject constructor(
    private val getMessagesUsecase: GetMessagesUsecase,
    private val addMessageUsecase: AddMessageUsecase,
    private val getUserUsecase: GetUserUsecase
) : ViewModel() {

    sealed class MessagesState {
        object Empty : MessagesState()
        object Failure : MessagesState()
        data class Success(val data: List<MessageModel>) : MessagesState()
    }

    val messagesStateLiveData: MutableLiveData<MessagesState> =
        MutableLiveData(MessagesState.Empty)


    val userIdLiveData: MutableLiveData<String> =
        MutableLiveData()

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


    fun loadCurrentUser() {
        getUserUsecase.invoke(viewModelScope, GetUserUsecase.Params(Unit)) {
            when (it) {
                is Outcome.Failure -> {
                }
                is Outcome.Success -> {
                    userIdLiveData.postValue(it.data.id)
                }
            }
        }
    }

    init {
        loadCurrentUser()
    }

}