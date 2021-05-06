package com.anteeone.coverit.di.components

import com.anteeone.coverit.App
import com.anteeone.coverit.di.modules.AppModule
import com.anteeone.coverit.di.modules.NetworkModule
import com.anteeone.coverit.di.modules.RepositoriesModule
import com.anteeone.coverit.di.modules.ViewModelModule
import com.anteeone.coverit.ui.views.auth.AuthActivity
import com.anteeone.coverit.ui.views.auth.LoginFragment
import com.anteeone.coverit.ui.views.auth.RegisterFragment
import com.anteeone.coverit.ui.views.domain.ChatFragment
import com.anteeone.coverit.ui.views.domain.HomeFragment
import com.anteeone.coverit.ui.views.domain.ProfileFragment
import com.anteeone.coverit.ui.views.domain.ProfileSettingsFragment
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

    //Activity
    fun inject(authActivity: AuthActivity)

    //Fragments
    fun inject(loginFragment: LoginFragment)
    fun inject(registerFragment: RegisterFragment)
    fun inject(homeFragment: HomeFragment)
    fun inject(profileFragment: ProfileFragment)
    fun inject(profileSettingsFragment: ProfileSettingsFragment)
    fun inject(chatFragment: ChatFragment)
}