package com.anteeone.coverit.domain.usecases.domain

import com.anteeone.coverit.domain.models.User
import com.anteeone.coverit.domain.repositories.IUsersRepository
import com.anteeone.coverit.domain.usecases.base.Usecase
import com.anteeone.coverit.domain.utils.Outcome
import java.lang.Exception
import javax.inject.Inject

class GetMatchingUsersUsecase @Inject constructor(
    private val usersRepository: IUsersRepository
): Usecase<List<User>, GetMatchingUsersUsecase.Params>() {

    override suspend fun run(params: Params): Outcome<List<User>> {
        try {
            val users = usersRepository.getAllMatchingUsers()
            return Outcome.Success(users)
        }
        catch (ex: Exception){
            return Outcome.Failure(ex)
        }
    }

    data class Params(val unit:Unit)

}