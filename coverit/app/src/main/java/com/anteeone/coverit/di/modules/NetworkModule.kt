package com.anteeone.coverit.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun providesFirebaseDatabase(): FirebaseFirestore =
        FirebaseFirestore.getInstance().also {
            it.firestoreSettings = FirebaseFirestoreSettings
                .Builder()
                .setPersistenceEnabled(false)
                .build()
        }

    @Provides
    @Singleton
    fun providesFireStorageReference(): StorageReference =
        FirebaseStorage.getInstance().reference
}