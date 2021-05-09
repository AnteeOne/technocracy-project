package com.anteeone.coverit.domain.usecases.domain

import com.anteeone.coverit.domain.models.User
import com.anteeone.coverit.domain.repositories.IUsersRepository
import com.anteeone.coverit.domain.usecases.base.Usecase
import com.anteeone.coverit.domain.utils.Outcome
import java.lang.Exception
import javax.inject.Inject

class GetUserUsecase @Inject constructor(
    private val usersRepository: IUsersRepository
): Usecase<User,GetUserUsecase.Params>() {

    override suspend fun run(params: Params): Outcome<User> {
        try {
            return Outcome.Success(usersRepository.getCurrentUser())
        }
        catch (ex:Exception){
            return Outcome.Failure(ex)
        }
    }

    data class Params(val data:Unit)
}