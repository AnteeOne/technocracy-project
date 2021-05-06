package com.anteeone.coverit.ui.viewmodels.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anteeone.coverit.domain.usecases.auth.SignInUsecase
import com.anteeone.coverit.domain.utils.Outcome
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val signInUsecase: SignInUsecase
) : ViewModel() {

    sealed class AuthenticationState {
        object Empty: AuthenticationState()
        object Failed: AuthenticationState()
        data class Authenticated(val firebaseUser: FirebaseUser): AuthenticationState()
    }

    val authStateLiveData: MutableLiveData<AuthenticationState> =
        MutableLiveData(AuthenticationState.Empty)

    fun login(email: String, password: String) {
        val params = SignInUsecase.Params(email, password);
        signInUsecase.invoke(viewModelScope, params) {
            when (it) {
                is Outcome.Success -> {
                    authStateLiveData.postValue(
                        AuthenticationState.Authenticated(it.data)
                    )
                    Log.println(Log.INFO, "coverit-tag", "User is authenticated!")
                }
                is Outcome.Failure -> {
                    authStateLiveData.postValue(AuthenticationState.Failed)
                    Log.println(Log.INFO, "coverit-tag", "User authentication is failed!")
                }
            }
        }

    }

}
