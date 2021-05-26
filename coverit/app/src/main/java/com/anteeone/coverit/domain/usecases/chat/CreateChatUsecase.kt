package com.anteeone.coverit.domain.usecases.chat

import com.anteeone.coverit.domain.models.MessageModel
import com.anteeone.coverit.domain.repositories.IChatRepository
import com.anteeone.coverit.domain.repositories.IUsersRepository
import com.anteeone.coverit.domain.usecases.base.Usecase
import com.anteeone.coverit.domain.utils.Outcome
import javax.inject.Inject

class CreateChatUsecase @Inject constructor(
    private val userRepository: IUsersRepository,
    private val chatRepository: IChatRepository
): Usecase<Unit, CreateChatUsecase.Params>() {

    override suspend fun run(params: Params): Outcome<Unit> {
        try {
            chatRepository.createChat(userRepository.getCurrentUser().id,params.receiverId)
            return Outcome.Success(Unit)
        }
        catch (ex: Exception){
            return Outcome.Failure(ex)
        }
    }

    data class Params(val receiverId: String)
}