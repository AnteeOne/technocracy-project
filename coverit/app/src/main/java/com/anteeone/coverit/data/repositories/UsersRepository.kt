package com.anteeone.coverit.data.repositories

import android.graphics.Bitmap
import com.anteeone.coverit.domain.models.User
import com.anteeone.coverit.domain.repositories.IUsersRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.IllegalStateException
import kotlin.coroutines.resume


class UsersRepository @Inject constructor(
    private val firebaseDatabase: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : IUsersRepository {

    private val usersCollection = firebaseDatabase.collection("users")

    override suspend fun getAllUsers(): List<User> = suspendCancellableCoroutine { cor ->
        try {
            usersCollection.get().addOnSuccessListener {
                it.toObjects(User::class.java).let { users ->
                    cor.resumeWith(Result.success(users))
                }
            }.addOnFailureListener {
                cor.resumeWith(Result.failure(IllegalStateException("Getting users error")))
            }
        } catch (ex: Exception) {
            cor.resumeWith(Result.failure(IllegalStateException("Getting users error")))
        }
    }

    override suspend fun getAllPotentialUsers(): List<User> = suspendCancellableCoroutine { cor ->
        try {
            val potentialUsers = usersCollection
            potentialUsers.get().addOnSuccessListener {
                it.toObjects(User::class.java).let { users ->
                    try {
                        val currentUser = users.filter { user -> user.id == firebaseAuth.uid }[0]
                        val exceptList: List<String> = currentUser.likes + currentUser.dislikes
                        val potentialUsersList = users
                            .filter { user -> user.id != firebaseAuth.currentUser.uid }
                            .filter { user -> !exceptList.contains(user.id) }
                        cor.resumeWith(Result.success(potentialUsersList))
                    } catch (ex: Exception) {
                        cor.resumeWith(Result.failure(IllegalStateException("Getting users error")))
                    }
                }
            }.addOnFailureListener {
                cor.resumeWith(Result.failure(IllegalStateException("Getting users error")))
            }
        } catch (ex: Exception) {
            cor.resumeWith(Result.failure(IllegalStateException("Getting users error")))
        }
    }

    override suspend fun getAllMatchingUsers(): List<User> = suspendCancellableCoroutine { cor ->
        try {
            usersCollection.get().addOnSuccessListener {
                it.toObjects(User::class.java).let { users ->
                    try {
                        val currentUserId = firebaseAuth.currentUser.uid
                        val currentUser = users.filter { user -> user.id == currentUserId }[0]
                        val potentialUsersList = users
                            .filter { user -> user.id != currentUserId }
                            .filter { user -> user.likes.contains(currentUserId) }
                            .filter { user -> currentUser.likes.contains(user.id) }
                        cor.resumeWith(Result.success(potentialUsersList))
                    } catch (ex: Exception) {
                        cor.resumeWith(Result.failure(IllegalStateException("Getting users error")))
                    }
                }
            }.addOnFailureListener {
                cor.resumeWith(Result.failure(IllegalStateException("Getting users error")))
            }
        } catch (ex: Exception) {
            cor.resumeWith(Result.failure(IllegalStateException("Getting users error")))
        }
    }

    override suspend fun addUser(user: User): User = suspendCancellableCoroutine { cor ->
        try {
            usersCollection.document(user.id).set(user.toMap()).addOnSuccessListener {
                cor.resumeWith(Result.success(user))
            }.addOnFailureListener {
                cor.resumeWith(Result.failure(IllegalStateException("Adding user error")))
            }
        } catch (ex: Exception) {
            cor.resumeWith(Result.failure(ex))
        }
    }

    override suspend fun updateCurrentUser(user: User): Unit = suspendCancellableCoroutine { cor ->
        try {
            usersCollection.document(firebaseAuth.uid!!)
                .set(user.toMapForUpdate(), SetOptions.merge()).addOnSuccessListener {
                    cor.resumeWith(Result.success(Unit))
                }.addOnFailureListener {
                    cor.resumeWith(Result.failure(IllegalStateException("Updating current user error")))
                }
        } catch (ex: Exception) {
            cor.resumeWith(Result.failure(ex))
        }
    }

    override suspend fun getCurrentUser(): User = suspendCancellableCoroutine { cor ->
        try {
            usersCollection.document(firebaseAuth.uid!!).get().addOnSuccessListener {
                val currentUser = it.toObject(User::class.java)!!
                cor.resumeWith(Result.success(currentUser))
            }.addOnFailureListener {
                cor.resumeWith(Result.failure(IllegalStateException("Getting current user error")))
            }
        } catch (ex: Exception) {
            cor.resumeWith(Result.failure(ex))
        }
    }

    override suspend fun getUserById(userId: String): User = suspendCancellableCoroutine { cor ->
        try {
            usersCollection.document(userId).get().addOnSuccessListener {
                val currentUser = it.toObject(User::class.java)!!
                cor.resumeWith(Result.success(currentUser))
            }.addOnFailureListener {
                cor.resumeWith(Result.failure(IllegalStateException("Getting user error")))
            }
        } catch (ex: Exception) {
            cor.resumeWith(Result.failure(ex))
        }
    }

    override suspend fun likeUser(userId: String): Unit = suspendCancellableCoroutine { cor ->
        try {
            usersCollection.document(firebaseAuth.uid!!)
                .update("likes", FieldValue.arrayUnion(userId)).addOnSuccessListener {
                    cor.resumeWith(Result.success(Unit))
                }.addOnFailureListener {
                    cor.resumeWith(Result.failure(IllegalStateException("Liking user error")))
                }
        } catch (ex: Exception) {
            cor.resumeWith(Result.failure(ex))
        }
    }

    override suspend fun dislikeUser(userId: String): Unit = suspendCancellableCoroutine { cor ->
        try {
            usersCollection.document(firebaseAuth.uid!!)
                .update("dislikes", FieldValue.arrayUnion(userId)).addOnSuccessListener {
                    cor.resumeWith(Result.success(Unit))
                }.addOnFailureListener {
                    cor.resumeWith(Result.failure(IllegalStateException("Disliking user error")))
                }
        } catch (ex: Exception) {
            cor.resumeWith(Result.failure(ex))
        }
    }
}