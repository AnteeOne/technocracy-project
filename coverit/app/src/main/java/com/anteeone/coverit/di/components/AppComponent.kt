package com.anteeone.coverit.di.components

import com.anteeone.coverit.App
import com.anteeone.coverit.di.modules.AppModule
import com.anteeone.coverit.di.modules.NetworkModule
import com.anteeone.coverit.di.modules.RepositoriesModule
import com.anteeone.coverit.di.modules.ViewModelModule
import com.anteeone.coverit.ui.views.auth.LoginFragment
import com.anteeone.coverit.ui.views.auth.RegisterFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
                      ViewModelModule::class,
                      NetworkModule::class,
                      RepositoriesModule::class]
)
interface AppComponent {
    //App
    fun inject(app: App)

    //Fragments
    fun inject(loginFragment: LoginFragment)
    fun inject(registerFragment: RegisterFragment)
}