package com.anteeone.coverit.domain.usecases.domain

import com.anteeone.coverit.domain.repositories.IUsersRepository
import com.anteeone.coverit.domain.usecases.base.Usecase
import com.anteeone.coverit.domain.utils.Outcome
import com.anteeone.coverit.ui.utils.extensions._log
import javax.inject.Inject

class CheckMatchingUsecase @Inject constructor(
    private val usersRepository: IUsersRepository
): Usecase<Boolean,CheckMatchingUsecase.Params>() {

    override suspend fun run(params: Params): Outcome<Boolean> {
        try {
            val user = usersRepository.getUserById(params.userId)
            val currentUser = usersRepository.getCurrentUser()
            return if(user.likes.contains(currentUser.id)) Outcome.Success(true)
            else Outcome.Success(false)
        }
        catch (ex: Exception){
            return Outcome.Failure(ex)
        }
    }

    data class Params(val userId: String)

}