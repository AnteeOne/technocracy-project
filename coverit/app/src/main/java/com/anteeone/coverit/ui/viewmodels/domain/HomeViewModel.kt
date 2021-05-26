package com.anteeone.coverit.ui.viewmodels.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anteeone.coverit.domain.models.User
import com.anteeone.coverit.domain.usecases.chat.CreateChatUsecase
import com.anteeone.coverit.domain.usecases.domain.CheckMatchingUsecase
import com.anteeone.coverit.domain.usecases.domain.DislikeUserUsecase
import com.anteeone.coverit.domain.usecases.domain.GetPotentialUsersUsecase
import com.anteeone.coverit.domain.usecases.domain.LikeUserUsecase
import com.anteeone.coverit.domain.utils.Outcome
import com.anteeone.coverit.ui.utils.extensions._log
import com.anteeone.coverit.ui.utils.models.Container
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getPotentialUsersUsecase: GetPotentialUsersUsecase,
    private val likeUserUsecase: LikeUserUsecase,
    private val dislikeUserUsecase: DislikeUserUsecase,
    private val checkMatchingUsecase: CheckMatchingUsecase,
    private val createChatUsecase: CreateChatUsecase
): ViewModel() {

    sealed class MatchedUserState(){
        object Empty: MatchedUserState()
        object Failure: MatchedUserState()
        data class Success(val user: User): MatchedUserState()

        fun pack(isForSubscribers: Boolean) = Container(this,isForSubscribers)
    }

    var users:MutableLiveData<MutableList<User>> =
        MutableLiveData()

    val matchedUserState: MutableLiveData<Container<MatchedUserState>> =
        MutableLiveData()

    init {
        loadUsersList()
    }

    fun loadUsersList(){
        _log("taking users from internet...")
        getPotentialUsersUsecase.invoke(viewModelScope,
            GetPotentialUsersUsecase.Params(Unit)){
            when(it){
                is Outcome.Success -> {
                    users.postValue(it.data as MutableList<User>)
                }
                is Outcome.Failure -> {
                    it.exception.printStackTrace()
                    //todo: handle bad outcome
                }
            }
        }
    }

    fun likeUser(){
        val user = users.value!![0]
        likeUserUsecase.invoke(viewModelScope,LikeUserUsecase.Params(user.id)){
            when(it){
                is Outcome.Success -> {
                    _log("deleting user...")
                    checkUserMatching(user)
                    users.value!!.removeAt(0)
                    notifyUsersObservers()
                    _log("actual size: ${users.value!!.size}")
                }
                is Outcome.Failure -> {
                    it.exception.printStackTrace()
                    //todo: handle bad outcome
                }
            }
        }
    }

    fun dislikeUser(){
        val user = users.value!![0]
        dislikeUserUsecase.invoke(viewModelScope,DislikeUserUsecase.Params(user.id)){
            when(it){
                is Outcome.Success -> {
                    _log("deleting user...")
                    users.value!!.removeAt(0)
                    notifyUsersObservers()
                    _log("actual size: ${users.value!!.size}")
                }
                is Outcome.Failure -> {
                    it.exception.printStackTrace()
                    //todo: handle bad outcome
                }
            }
        }
    }

    private fun notifyUsersObservers(){
        users.postValue(users.value)
    }

    private fun checkUserMatching(user: User){
        checkMatchingUsecase.invoke(viewModelScope,CheckMatchingUsecase.Params(user.id)){
            when(it){
                is Outcome.Success -> {
                    if(it.data) {
                        matchedUserState.postValue(Container(MatchedUserState.Success(user), true))
                        createChatUsecase.invoke(viewModelScope,CreateChatUsecase.Params(user.id))
                    }
                }
                is Outcome.Failure -> {

                }
            }
        }
    }


}