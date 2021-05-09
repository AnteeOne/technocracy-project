package com.anteeone.coverit.domain.repositories

import com.google.firebase.auth.FirebaseUser

interface IAuthRepository {

    suspend fun login(email: String, password: String): FirebaseUser?;

    suspend fun register(email: String, password: String, repeatPassword: String): Boolean;

}