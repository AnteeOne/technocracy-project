package com.anteeone.coverit.ui.utils.models

open class SealedState {

    fun pack(isForSubscribers: Boolean) = Container(this,isForSubscribers)
}