package com.anteeone.coverit.domain.repositories

import android.graphics.Bitmap
import com.anteeone.coverit.domain.models.User

interface IUsersRepository {

    suspend fun getAllUsers():List<User>

    suspend fun getAllPotentialUsers():List<User>

    suspend fun getAllMatchingUsers(): List<User>

    suspend fun addUser(user: User): User

    suspend fun updateCurrentUser(user: User)

    suspend fun getCurrentUser(): User

    suspend fun likeUser(userId: String): Unit

    suspend fun dislikeUser(userId: String): Unit


}