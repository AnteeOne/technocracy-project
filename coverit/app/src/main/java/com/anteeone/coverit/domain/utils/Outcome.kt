package com.anteeone.coverit.domain.utils

import java.lang.Exception

sealed class Outcome<out T> {
    class Success<out T>(val data: T): Outcome<T>()
    class Failure(val exception: Exception): Outcome<Nothing>()

    fun onResult(onSuccess: () -> Unit,
                 onFailure: () -> Unit){
        when(this){
            is Success -> onSuccess
            is Failure -> onFailure
        }
    }

}

