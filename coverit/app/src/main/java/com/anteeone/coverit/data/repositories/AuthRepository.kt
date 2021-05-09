package com.anteeone.coverit.data.repositories

import com.anteeone.coverit.domain.models.User
import com.anteeone.coverit.domain.repositories.IAuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import java.lang.NullPointerException
import kotlin.coroutines.resumeWithException

class AuthRepository(private val firebaseAuth: FirebaseAuth): IAuthRepository {

    //todo:replace auth logic with injecting auth by dagger2

    override suspend fun login(email: String, password: String): FirebaseUser = suspendCancellableCoroutine {cor ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    it.user?.let {
                        cor.resumeWith(Result.success(it))
                    } ?: run {
                        cor.resumeWithException(NullPointerException("User is null"))
                    }
                }.addOnFailureListener{
                    cor.resumeWithException(NullPointerException("sf"))
                }
    }


    override suspend fun register(email: String, password: String, repeatPassword: String): Boolean = suspendCancellableCoroutine{
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                true
            }.addOnFailureListener {
                false
            } 
    }
}