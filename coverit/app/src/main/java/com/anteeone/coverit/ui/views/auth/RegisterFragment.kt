package com.anteeone.coverit.ui.views.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.anteeone.coverit.R
import com.anteeone.coverit.ui.utils.extensions.insertViewModel
import com.anteeone.coverit.ui.utils.extensions.showToast
import com.anteeone.coverit.ui.utils.validators.AuthValidator
import com.anteeone.coverit.ui.utils.validators.AuthValidator.SignUpResult
import com.anteeone.coverit.ui.viewmodels.LoginViewModel
import com.anteeone.coverit.ui.viewmodels.RegisterViewModel
import com.anteeone.coverit.ui.views.BaseFragment

class RegisterFragment : BaseFragment() {

    private lateinit var viewModel: RegisterViewModel

    private lateinit var mRegisterButton: Button
    private lateinit var mEmailEditText: EditText
    private lateinit var mPasswordEditText: EditText
    private lateinit var mRepeatPasswordEditText: EditText
    private lateinit var mBackButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        insertDependencies()
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        initMembers(view)
        initNavigation()
        initListeners()
        return view;
    }

    override fun insertDependencies() {
        appComponent.inject(this)
    }

    override fun initMembers(view: View) {
        mRegisterButton = view.findViewById(R.id.fr_register_button_signup)
        mBackButton = view.findViewById(R.id.fr_register_button_back)
        mEmailEditText = view.findViewById(R.id.fr_register_et_email)
        mPasswordEditText = view.findViewById(R.id.fr_register_et_password)
        mRepeatPasswordEditText = view.findViewById(R.id.fr_register_et_rpassword)
    }

    override fun initListeners() {
        mBackButton.setOnClickListener{
            navController.popBackStack()
        }
        mRegisterButton.setOnClickListener{
            handleSignUpForm()
        }
    }

    override fun initNavigation() {
        navController = findNavController()
    }

    override fun initViewModel() {
        viewModel = insertViewModel(viewModelFactory)
        viewModel.also {
            it.authStateLiveData.observe(this,onAuthStateChange)
        }
    }

    private val onAuthStateChange: (RegisterViewModel.AuthenticationState)-> Unit = { authState ->
        Log.println(Log.INFO, "coverit-tag", "RegisterVM: Auth state has been changed!")
        when (authState) {
            is RegisterViewModel.AuthenticationState.Empty -> {
            }
            is RegisterViewModel.AuthenticationState.Failed -> {
                showToast("Auth error")
                context?.let { showToast(it, "Auth error") }
            }
            is RegisterViewModel.AuthenticationState.Success -> {
                showToast("Account has been created!")
                navController.popBackStack()
            }
        }
    }

    private fun handleSignUpForm(){
        when(AuthValidator.validateSignUpForm(
            mEmailEditText.text.toString(),
            mPasswordEditText.text.toString(),
            mRepeatPasswordEditText.text.toString())
        ){
            SignUpResult.INVALID_EMAIL -> {showToast("Invalid email!")}
            SignUpResult.INVALID_PASSWORD -> {showToast("Invalid password!")}
            SignUpResult.PASSWORDS_DIDNT_MATCH -> {showToast("Passwords didn't match!")}
            SignUpResult.VALID -> {
                viewModel.register(
                    mEmailEditText.text.toString(),
                    mPasswordEditText.text.toString()
                )
            }
        }
    }

}