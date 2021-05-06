package com.anteeone.coverit.domain.usecases.domain

import com.anteeone.coverit.domain.models.User
import com.anteeone.coverit.domain.repositories.IUsersRepository
import com.anteeone.coverit.domain.usecases.base.Usecase
import com.anteeone.coverit.domain.utils.Outcome
import java.lang.Exception
import javax.inject.Inject

class GetPotentialUsersUsecase @Inject constructor(
    private val usersRepository: IUsersRepository
): Usecase<List<User>, GetPotentialUsersUsecase.Params>() {

    override suspend fun run(params: Params): Outcome<List<User>> {
        try {
            val users = usersRepository.getAllPotentialUsers()
            return Outcome.Success(users)
        }
        catch (ex:Exception){
            ex.printStackTrace()
            return Outcome.Failure(ex)
        }
    }

    data class Params(val unit:Unit)

}