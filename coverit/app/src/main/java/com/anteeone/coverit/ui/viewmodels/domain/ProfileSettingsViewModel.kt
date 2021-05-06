package com.anteeone.coverit.ui.viewmodels.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anteeone.coverit.domain.models.User
import com.anteeone.coverit.domain.usecases.domain.GetUserUsecase
import com.anteeone.coverit.domain.usecases.domain.UpdateUserUsecase
import com.anteeone.coverit.domain.utils.Outcome
import javax.inject.Inject

class ProfileSettingsViewModel @Inject constructor(
    private val getUserUsecase: GetUserUsecase,
    private val updateUserUsecase: UpdateUserUsecase
): ViewModel() {

    sealed class UserDataState{
        object Empty: UserDataState()
        object Failed: UserDataState()
        data class Success(val user :User): UserDataState()
    }

    sealed class UserUpdatingState{
        object Empty: UserUpdatingState()
        object Failed: UserUpdatingState()
        object Success: UserUpdatingState()
    }

    val userStateLiveData: MutableLiveData<UserDataState> =
        MutableLiveData(UserDataState.Empty)

    val userUpdatingStateLiveData: MutableLiveData<UserUpdatingState> =
        MutableLiveData(UserUpdatingState.Empty)

    fun loadData(){
        getUserUsecase.invoke(viewModelScope,GetUserUsecase.Params(Unit)){
            when(it){
                is Outcome.Success -> {
                    userStateLiveData.postValue(UserDataState.Success(it.data))
                }
                is Outcome.Failure -> {
                    userStateLiveData.postValue(UserDataState.Failed)
                }
            }
        }
    }

    fun saveData(user: User){
        updateUserUsecase.invoke(viewModelScope,UpdateUserUsecase.Params(user)){
            when(it){
                is Outcome.Success -> {
                    userUpdatingStateLiveData.postValue(UserUpdatingState.Success)
                }
                is Outcome.Failure -> {
                    userUpdatingStateLiveData.postValue(UserUpdatingState.Failed)
                }
            }
        }
    }

}