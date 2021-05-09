package com.anteeone.coverit.ui.utils.extensions

import java.util.regex.Pattern

fun String.isEmail(): Boolean{
    val emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\$"
    return matches(Regex(emailRegex))
}

fun String.isPassword(): Boolean{
    return this.length >= 6
}