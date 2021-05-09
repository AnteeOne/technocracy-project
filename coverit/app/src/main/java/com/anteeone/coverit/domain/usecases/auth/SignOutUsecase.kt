package com.anteeone.coverit.domain.usecases.auth

import com.anteeone.coverit.domain.repositories.IAuthRepository
import com.anteeone.coverit.domain.usecases.base.Usecase
import com.anteeone.coverit.domain.utils.Outcome
import javax.inject.Inject

class SignOutUsecase @Inject constructor(
    private val authRepository: IAuthRepository
): Usecase<Unit, SignOutUsecase.Params>() {

    override suspend fun run(params: Params): Outcome<Unit> {
        try {
            authRepository.logout()
            return Outcome.Success(Unit)
        }
        catch (ex: Exception){
            return Outcome.Failure(ex)
        }
    }

    class Params()

}