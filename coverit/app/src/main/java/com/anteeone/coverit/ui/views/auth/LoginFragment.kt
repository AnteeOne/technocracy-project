package com.anteeone.coverit.ui.views.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.anteeone.coverit.R
import com.anteeone.coverit.ui.utils.extensions.insertViewModel
import com.anteeone.coverit.ui.utils.extensions.showToast
import com.anteeone.coverit.ui.utils.validators.AuthValidator
import com.anteeone.coverit.ui.utils.validators.AuthValidator.SignInResult
import com.anteeone.coverit.ui.viewmodels.LoginViewModel
import com.anteeone.coverit.ui.views.BaseFragment

class LoginFragment : BaseFragment() {

    private lateinit var viewModel: LoginViewModel

    private lateinit var mLoginButton: Button
    private lateinit var mEmailEditText: EditText
    private lateinit var mPasswordEditText: EditText
    private lateinit var mRegisterButton: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        insertDependencies()
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        initMembers(view)
        initNavigation()
        initListeners()

        return view;
    }

    override fun insertDependencies() {
        appComponent.inject(this)
    }

    override fun initMembers(view: View) {
        view.let {
            mLoginButton = it.findViewById(R.id.fr_login_button_signin)
            mEmailEditText = it.findViewById(R.id.fr_login_et_email)
            mPasswordEditText = it.findViewById(R.id.fr_login_et_password)
            mRegisterButton = it.findViewById(R.id.fr_login_button_signup)
        }
    }

    override fun initNavigation() {
        navController = findNavController()
    }

    override fun initListeners() {
        mLoginButton.setOnClickListener {
            handleSignInForm()
        }
        mRegisterButton.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun initViewModel() {
        viewModel = insertViewModel(viewModelFactory)
        viewModel.also {
            it.authStateLiveData.observe(this,onAuthStateChange)
        }
    }

    private val onAuthStateChange: (LoginViewModel.AuthenticationState)-> Unit = { authState ->
        Log.println(Log.INFO, "coverit-tag", "LoginVM: Auth state has been changed!")
        when (authState) {
            is LoginViewModel.AuthenticationState.Empty -> {
            }
            is LoginViewModel.AuthenticationState.Failed -> {
                showToast("Incorrect Email OR Password!")
            }
            is LoginViewModel.AuthenticationState.Authenticated -> {
                showToast(authState.firebaseUser.email)
            }
        }
    }

    private fun handleSignInForm(){
        when(AuthValidator.validateSignInForm(
            mEmailEditText.text.toString(),
            mPasswordEditText.text.toString())
        ){
            SignInResult.INVALID_EMAIL -> {
                showToast("Invalid email!")
            }
            SignInResult.INVALID_PASSWORD -> {
                showToast("Invalid password!")
            }
            SignInResult.VALID -> {
                viewModel.login(
                    mEmailEditText.text.toString(),
                    mPasswordEditText.text.toString()
                )
            }
        }
    }
}