package com.anteeone.coverit.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anteeone.coverit.domain.usecases.auth.SignUpUsecase
import com.anteeone.coverit.domain.utils.Outcome
import javax.inject.Inject

class RegisterViewModel @Inject constructor(
    private val signUpUsecase: SignUpUsecase
): ViewModel() {

    sealed class AuthenticationState {
        object Empty: AuthenticationState()
        object Failed: AuthenticationState()
        object Success: AuthenticationState()
    }

    val authStateLiveData: MutableLiveData<AuthenticationState> =
        MutableLiveData(AuthenticationState.Empty)

    fun register(email: String, password: String){
        val params = SignUpUsecase.Params(email, password)
        signUpUsecase.invoke(viewModelScope,params){
            when(it){
                is Outcome.Success -> {
                    authStateLiveData.postValue(AuthenticationState.Success)
                }
                is Outcome.Failure -> {
                    authStateLiveData.postValue(AuthenticationState.Failed)
                }
            }
        }
    }
}