package com.anteeone.coverit.domain.usecases.auth

import com.anteeone.coverit.domain.repositories.IAuthRepository
import com.anteeone.coverit.domain.usecases.base.Usecase
import com.anteeone.coverit.domain.utils.Outcome
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class SignInUsecase @Inject constructor(
    private val authRepository: IAuthRepository
) : Usecase<FirebaseUser, SignInUsecase.Params>() {

    override suspend fun run(params: Params): Outcome<FirebaseUser> {
        return try {
            val firebaseUser: FirebaseUser = authRepository.login(params.email, params.password)
            Outcome.Success(firebaseUser)
        } catch (ex: Exception){
            Outcome.Failure(ex);
        }
    }

    fun getCurrentUser(): Outcome<FirebaseUser> {
        try {
            val firebaseUser = authRepository.getCurrentUser()
            if (firebaseUser != null)
                return Outcome.Success(firebaseUser)
            return Outcome.Failure(IllegalStateException("Cache user is null"))
        } catch (ex: Exception) {
            return Outcome.Failure(ex)
        }
    }
    data class Params(val email: String,
                      val password: String)
}