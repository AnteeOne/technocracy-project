package com.anteeone.coverit.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anteeone.coverit.di.annotations.ViewModelKey
import com.anteeone.coverit.ui.viewmodels.auth.LoginViewModel
import com.anteeone.coverit.ui.viewmodels.auth.RegisterViewModel
import com.anteeone.coverit.ui.viewmodels.domain.*
import com.anteeone.coverit.ui.viewmodels.factory.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    @Singleton
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindsLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindsRegisterViewModel(registerViewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindsHomeViewModel(registerViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileSettingsViewModel::class)
    abstract fun bindsProfileSettingsViewModel(profileSettingsViewModel: ProfileSettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MatchesViewModel::class)
    abstract fun bindsMatchesViewModel(matchesViewModel: MatchesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindsProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChartsViewModel::class)
    abstract fun bindsChartsViewModel(chartsViewModel: ChartsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChatDetailsViewModel::class)
    abstract fun bindsChatDetailsViewModel(chatDetailsViewModel: ChatDetailsViewModel): ViewModel
}