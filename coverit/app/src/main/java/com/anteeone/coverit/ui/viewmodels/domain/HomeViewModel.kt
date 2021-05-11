package com.anteeone.coverit.ui.viewmodels.domain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anteeone.coverit.domain.models.User
import com.anteeone.coverit.domain.usecases.domain.DislikeUserUsecase
import com.anteeone.coverit.domain.usecases.domain.GetPotentialUsersUsecase
import com.anteeone.coverit.domain.usecases.domain.LikeUserUsecase
import com.anteeone.coverit.domain.utils.Outcome
import com.anteeone.coverit.ui.utils.extensions._log
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getPotentialUsersUsecase: GetPotentialUsersUsecase,
    private val likeUserUsecase: LikeUserUsecase,
    private val dislikeUserUsecase: DislikeUserUsecase
): ViewModel() {

    var users:MutableLiveData<MutableList<User>> =
        MutableLiveData(mutableListOf())

    fun loadUsersList(){
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
        likeUserUsecase.invoke(viewModelScope,LikeUserUsecase.Params(users.value!![0].id)){
            when(it){
                is Outcome.Success -> {
                    _log("deleting user...")
                    users.value!!.removeAt(0)
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
        dislikeUserUsecase.invoke(viewModelScope,DislikeUserUsecase.Params(users.value!![0].id)){
            when(it){
                is Outcome.Success -> {
                    _log("deleting user...")
                    users.value!!.removeAt(0)
                    _log("actual size: ${users.value!!.size}")
                }
                is Outcome.Failure -> {
                    it.exception.printStackTrace()
                    //todo: handle bad outcome
                }
            }
        }
    }

}