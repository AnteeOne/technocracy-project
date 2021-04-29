package com.anteeone.coverit.di.modules

import com.anteeone.coverit.data.repositories.AuthRepository
import com.anteeone.coverit.domain.repositories.IAuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth):IAuthRepository =
        AuthRepository(firebaseAuth)
}