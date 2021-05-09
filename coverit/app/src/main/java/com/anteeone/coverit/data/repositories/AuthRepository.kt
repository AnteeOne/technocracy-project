package com.anteeone.coverit.data.repositories

import android.util.Log
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

    override suspend fun register(email: String, password: String): FirebaseUser = suspendCancellableCoroutine{cor ->
        try {
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener {
                    cor.resumeWith(Result.success(it.user!!))
                }.addOnFailureListener {
                    cor.resumeWith(Result.failure(IllegalStateException("User sign up error")))
                }
        }
        catch (ex: Exception){
            cor.resumeWith(Result.failure(ex))
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
            val firebaseUser = firebaseAuth.currentUser
            Log.println(Log.INFO,"coverit-tag","Auto logged in as ${firebaseUser.uid}")
            return firebaseUser
        }

    override suspend fun logout(): Unit = suspendCancellableCoroutine {cor ->
        try{
            firebaseAuth.signOut()
            cor.resumeWith(Result.success(Unit))
        }
        catch (ex: Exception){
            cor.resumeWith(Result.failure(IllegalStateException("Sign out error")))
        }
    }
}