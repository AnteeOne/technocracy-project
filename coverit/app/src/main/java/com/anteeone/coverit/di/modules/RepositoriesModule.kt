package com.anteeone.coverit.di.modules

import com.anteeone.coverit.data.repositories.AuthRepository
import com.anteeone.coverit.data.repositories.UsersRepository
import com.anteeone.coverit.domain.repositories.IAuthRepository
import com.anteeone.coverit.domain.repositories.IUsersRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth):IAuthRepository =
        AuthRepository(firebaseAuth)

    @Provides
    @Singleton
    fun provideUserRepository(firebaseDatabase: FirebaseFirestore,firebaseAuth: FirebaseAuth):IUsersRepository =
        UsersRepository(firebaseDatabase,firebaseAuth)
}