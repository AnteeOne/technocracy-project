package com.anteeone.coverit.domain.usecases.domain

import com.anteeone.coverit.domain.models.User
import com.anteeone.coverit.domain.repositories.IUsersRepository
import com.anteeone.coverit.domain.usecases.base.Usecase
import com.anteeone.coverit.domain.utils.Outcome
import javax.inject.Inject

class UpdateUserUsecase @Inject constructor(
    private val usersRepository: IUsersRepository
) : Usecase<Unit, UpdateUserUsecase.Params>() {

    override suspend fun run(params: Params): Outcome<Unit> {
        try {
            return Outcome.Success(usersRepository.updateCurrentUser(params.user))
        } catch (ex: Exception) {
            return Outcome.Failure(ex)
        }
    }

    data class Params(val user: User)
}