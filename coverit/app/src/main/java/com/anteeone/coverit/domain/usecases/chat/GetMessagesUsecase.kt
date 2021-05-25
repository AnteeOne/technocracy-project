package com.anteeone.coverit.domain.usecases.chat

import com.anteeone.coverit.domain.models.MessageModel
import com.anteeone.coverit.domain.repositories.IChatRepository
import com.anteeone.coverit.domain.repositories.IUsersRepository
import com.anteeone.coverit.domain.usecases.base.Usecase
import com.anteeone.coverit.domain.utils.Outcome
import javax.inject.Inject

class GetMessagesUsecase @Inject constructor(
    private val userRepository: IUsersRepository,
    private val chatRepository: IChatRepository
): Usecase<List<MessageModel>, GetMessagesUsecase.Params>() {

    override suspend fun run(params: Params): Outcome<List<MessageModel>> {
        try {
            return Outcome.Success(
                chatRepository.getAllMessages(
                    userRepository.getCurrentUser().id,
                    params.receiverId
                )
            )
        }
        catch (ex: Exception){
            return Outcome.Failure(ex)
        }
    }

    data class Params(val receiverId: String)

}