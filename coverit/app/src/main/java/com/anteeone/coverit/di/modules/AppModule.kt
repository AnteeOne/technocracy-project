package com.anteeone.coverit.di.modules

import android.app.Application
import android.content.Context
import com.anteeone.coverit.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: App) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application


}