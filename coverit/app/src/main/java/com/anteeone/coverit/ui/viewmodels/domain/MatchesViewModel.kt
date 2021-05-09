package com.anteeone.coverit.ui.viewmodels.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anteeone.coverit.domain.models.User
import com.anteeone.coverit.domain.usecases.domain.GetMatchingUsersUsecase
import com.anteeone.coverit.domain.utils.Outcome
import com.anteeone.coverit.ui.utils.models.Container
import javax.inject.Inject

class MatchesViewModel @Inject constructor(
    private val getMatchingUsersUsecase: GetMatchingUsersUsecase
): ViewModel() {

    sealed class UsersState{
        object Empty: UsersState()
        object Failure: UsersState()
        data class Success(val data: List<User>): UsersState()

        fun pack(isForSubscribers: Boolean) = Container(this,isForSubscribers)
    }

    val users: MutableLiveData<Container<UsersState>> =
        MutableLiveData(UsersState.Empty.pack(true))

    fun loadUsers(){
        getMatchingUsersUsecase(viewModelScope,GetMatchingUsersUsecase.Params(Unit)){
            when(it){
                is Outcome.Success -> {
                    users.postValue(UsersState.Success(it.data).pack(true))
                }
                is Outcome.Failure -> {
                    users.postValue(UsersState.Failure.pack(true))
                }
            }
        }
    }
}