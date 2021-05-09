package com.anteeone.coverit.ui.utils.models

import java.io.Serializable


data class Container<out A>(
    val data: A,
    val forSubscribers: Boolean
) : Serializable {

    fun value() = data

    fun isForSubscribers() = forSubscribers

}