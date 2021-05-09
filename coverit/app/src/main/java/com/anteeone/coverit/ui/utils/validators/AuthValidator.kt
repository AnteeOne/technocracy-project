package com.anteeone.coverit.ui.utils.validators

import com.anteeone.coverit.ui.utils.extensions.isEmail
import com.anteeone.coverit.ui.utils.extensions.isPassword

object AuthValidator {

    fun validateSignInForm(email: String,password: String): SignInResult =
        when{
            !email.isEmail() -> SignInResult.INVALID_EMAIL
            !password.isPassword() -> SignInResult.INVALID_PASSWORD
            else -> SignInResult.VALID
        }

    fun validateSignUpForm(email: String,password: String,repeatPassword: String): SignUpResult =
        when{
            !email.isEmail() -> SignUpResult.INVALID_EMAIL
            !password.isPassword() -> SignUpResult.INVALID_PASSWORD
            password != repeatPassword -> SignUpResult.PASSWORDS_DIDNT_MATCH
            else -> SignUpResult.VALID
        }

    enum class SignInResult{
        VALID,
        INVALID_EMAIL,
        INVALID_PASSWORD
    }

    enum class SignUpResult{
        VALID,
        INVALID_EMAIL,
        INVALID_PASSWORD,
        PASSWORDS_DIDNT_MATCH
    }

}