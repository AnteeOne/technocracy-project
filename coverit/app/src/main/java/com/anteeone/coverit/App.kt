package com.anteeone.coverit

import android.app.Application
import com.anteeone.coverit.di.components.AppComponent
import com.anteeone.coverit.di.components.DaggerAppComponent

class App: Application() {

    val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerAppComponent
            .builder()
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
    }

    private fun injectMembers() = appComponent.inject(this)
}