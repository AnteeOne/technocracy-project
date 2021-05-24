package com.anteeone.coverit.di.components

import com.anteeone.coverit.App
import com.anteeone.coverit.di.modules.*
import com.anteeone.coverit.ui.views.auth.AuthActivity
import com.anteeone.coverit.ui.views.auth.LoginFragment
import com.anteeone.coverit.ui.views.auth.RegisterFragment
import com.anteeone.coverit.ui.views.domain.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
                      ViewModelModule::class,
                      FirebaseModule::class,
                      RepositoriesModule::class,
                      RetrofitModule::class]
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
    fun inject(matchesFragment: MatchesFragment)
    fun inject(chartsFragment: ChartsFragment)

    //todo: replace with sub-components and dagger scopes
}