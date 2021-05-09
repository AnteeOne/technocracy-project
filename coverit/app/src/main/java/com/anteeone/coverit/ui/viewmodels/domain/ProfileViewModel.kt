package com.anteeone.coverit.ui.viewmodels.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anteeone.coverit.domain.models.User
import com.anteeone.coverit.domain.usecases.auth.SignOutUsecase
import com.anteeone.coverit.domain.usecases.domain.GetUserUsecase
import com.anteeone.coverit.domain.utils.Outcome
import com.anteeone.coverit.ui.utils.models.Container
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getUserUsecase: GetUserUsecase,
    private val signOutUsecase: SignOutUsecase
): ViewModel() {

    sealed class UserState{
        object Empty: UserState()
        object Failure: UserState()
        data class Success(val data: User): UserState()

        fun pack(isForSubscribers: Boolean) = Container(this,isForSubscribers)
    }

    val userState: MutableLiveData<Container<UserState>> =
        MutableLiveData(UserState.Empty.pack(true))

    fun loadUser(){
        getUserUsecase(viewModelScope,GetUserUsecase.Params(Unit)){
            when(it){
                is Outcome.Failure -> {
                    userState.postValue(UserState.Failure.pack(true))
                }
                is Outcome.Success -> {
                    userState.postValue(UserState.Success(it.data).pack(true))
                }
            }
        }
    }

    fun signOut(){
        signOutUsecase(viewModelScope,SignOutUsecase.Params())
    }

}