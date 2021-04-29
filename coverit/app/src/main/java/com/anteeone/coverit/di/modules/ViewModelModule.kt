package com.anteeone.coverit.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anteeone.coverit.di.annotations.ViewModelKey
import com.anteeone.coverit.ui.viewmodels.LoginViewModel
import com.anteeone.coverit.ui.viewmodels.RegisterViewModel
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

}