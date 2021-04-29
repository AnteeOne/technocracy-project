package com.anteeone.coverit.di.modules

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

}