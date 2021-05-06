package com.anteeone.coverit.domain.usecases.auth

import android.util.Log
import com.anteeone.coverit.domain.repositories.IAuthRepository
import com.anteeone.coverit.domain.usecases.base.Usecase
import com.anteeone.coverit.domain.utils.Outcome
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception
import javax.inject.Inject

class CheckUserAuthUsecase @Inject constructor(
    private val authRepository: IAuthRepository
): Usecase<FirebaseUser,CheckUserAuthUsecase.Params>() {


    override suspend fun run(params: Params): Outcome<FirebaseUser> {
        try {
            val firebaseUser = authRepository.getCurrentUser()
            if (firebaseUser != null)
                return Outcome.Success(firebaseUser)
            return Outcome.Failure(IllegalStateException("Cache user is null"))
        } catch (ex: Exception) {
            return Outcome.Failure(ex)
        }
    }

    fun getCurrentUser(): Outcome<FirebaseUser>{
        try {
            val firebaseUser = authRepository.getCurrentUser()
            if (firebaseUser != null)
                return Outcome.Success(firebaseUser)
            return Outcome.Failure(IllegalStateException("Cache user is null"))
        } catch (ex: Exception) {
            return Outcome.Failure(ex)
        }
    }

    data class Params(val unit: Unit)
}