package com.anteeone.coverit.domain.usecases.auth

import android.util.Log
import com.anteeone.coverit.domain.models.User
import com.anteeone.coverit.domain.repositories.IAuthRepository
import com.anteeone.coverit.domain.repositories.IUsersRepository
import com.anteeone.coverit.domain.usecases.base.Usecase
import com.anteeone.coverit.domain.utils.Outcome
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception
import javax.inject.Inject

class SignUpUsecase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val usersRepository: IUsersRepository
): Usecase<Unit,SignUpUsecase.Params>() {

    override suspend fun run(params: Params): Outcome<Unit> {
        try {
            val firebaseUser = authRepository.register(params.email,params.password)
            usersRepository.addUser(User(
                firebaseUser.uid,
                firebaseUser.email
            ))
            Log.println(Log.INFO,"coverit-tag","${params.email} account has been created!")
            return Outcome.Success(Unit)
        } catch (ex: Exception){
            Log.println(Log.ERROR,"coverit-tag","${params.email} account hasn't been created!")
            return Outcome.Failure(ex);
        }
    }

    data class Params(val email: String,
                      val password: String)
}