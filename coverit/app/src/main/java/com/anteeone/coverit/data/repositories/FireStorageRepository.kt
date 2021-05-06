package com.anteeone.coverit.data.repositories

import android.graphics.Bitmap
import com.anteeone.coverit.domain.repositories.IFireStorageRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.lang.IllegalStateException
import javax.inject.Inject

class FireStorageRepository @Inject constructor(
    private val storageReference: StorageReference,
    private val firebaseAuth: FirebaseAuth
) : IFireStorageRepository {

    private val AVATARS_PATH = "avatars/"

    override suspend fun updateCurrentUserAvatar(bitmap: Bitmap): String =
        suspendCancellableCoroutine { cor ->
            try {
                val currentUserId = firebaseAuth.currentUser.uid
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                storageReference.child(AVATARS_PATH + currentUserId)
                    .putBytes(baos.toByteArray())
                    .addOnSuccessListener {
                        storageReference.child(AVATARS_PATH + currentUserId).downloadUrl.addOnSuccessListener {
                            cor.resumeWith(Result.success(it.toString()))
                        }.addOnFailureListener {
                            cor.resumeWith(Result.failure(IllegalStateException("Getting user avatar url error")))
                        }
                    }.addOnFailureListener {
                        cor.resumeWith(Result.failure(IllegalStateException("Updating user avatar error")))
                    }
            } catch (ex: Exception) {
                cor.resumeWith(Result.failure(ex))
            }
        }
}