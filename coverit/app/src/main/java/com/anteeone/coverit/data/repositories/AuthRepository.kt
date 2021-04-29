package com.anteeone.coverit.data.repositories

import com.anteeone.coverit.domain.repositories.IAuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.suspendCancellableCoroutine

class AuthRepository(private val firebaseAuth: FirebaseAuth): IAuthRepository {

    override suspend fun login(email: String, password: String): FirebaseUser = suspendCancellableCoroutine {cor ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    it.user?.let {
                        cor.resumeWith(Result.success(it))
                    } ?: run {
                        cor.resumeWith(Result.failure(IllegalStateException("User is null")));
                    }
                }.addOnFailureListener{
                    cor.resumeWith(Result.failure(IllegalStateException("Sign in error")));
                }
    }


    override suspend fun register(email: String, password: String): Boolean = suspendCancellableCoroutine{cor ->
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                cor.resumeWith(Result.success(true))
            }.addOnFailureListener {
                cor.resumeWith(Result.success(false))
            } 
    }
}