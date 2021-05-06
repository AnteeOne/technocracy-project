package com.anteeone.coverit.domain.usecases.domain

import com.anteeone.coverit.domain.repositories.IUsersRepository
import com.anteeone.coverit.domain.usecases.base.Usecase
import com.anteeone.coverit.domain.utils.Outcome
import java.lang.Exception
import javax.inject.Inject

class DislikeUserUsecase @Inject constructor(
    private val usersRepository: IUsersRepository
) : Usecase<Unit, DislikeUserUsecase.Params>() {

    override suspend fun run(params: Params): Outcome<Unit> {
        try {
            usersRepository.dislikeUser(params.userId)
            return Outcome.Success(Unit)
        } catch (ex: Exception){
            return Outcome.Failure(ex)
        }
    }

    data class Params(val userId: String)
}