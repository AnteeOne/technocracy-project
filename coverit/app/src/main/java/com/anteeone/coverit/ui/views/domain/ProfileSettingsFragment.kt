package com.anteeone.coverit.ui.views.domain

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.anteeone.coverit.R
import com.anteeone.coverit.domain.models.User
import com.anteeone.coverit.ui.utils.extensions._log
import com.anteeone.coverit.ui.utils.extensions.insertViewModel
import com.anteeone.coverit.ui.viewmodels.domain.ProfileSettingsViewModel
import com.anteeone.coverit.ui.views.BaseFragment

class ProfileSettingsFragment : BaseFragment() {

    private lateinit var viewModel: ProfileSettingsViewModel

    private lateinit var mBackButton: ImageView
    private lateinit var mUpdateButton: Button
    private lateinit var mNameEditText: EditText
    private lateinit var mAgeEditText: EditText
    private lateinit var mSexEditText: EditText
    private lateinit var mRoleEditText: EditText
    private lateinit var mAboutEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        insertDependencies()
        initViewModel()
        _log("ProfileSettingsFragment has been created!")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_settings, container, false)
        initMembers(view)
        initNavigation()
        initListeners()
        return view
    }

    override fun insertDependencies() {
        appComponent.inject(this)
    }

    override fun initMembers(view: View) {
        mBackButton = view.findViewById(R.id.fr_profile_settings_btn_back)
        mUpdateButton = view.findViewById(R.id.fr_profile_settings_btn_update)
        mNameEditText = view.findViewById(R.id.fr_profile_settings_et_name)
        mAgeEditText = view.findViewById(R.id.fr_profile_settings_et_age)
        mSexEditText = view.findViewById(R.id.fr_profile_settings_et_sex)
        mRoleEditText = view.findViewById(R.id.fr_profile_settings_et_role)
        mAboutEditText = view.findViewById(R.id.fr_profile_settings_et_about)
    }

    override fun initListeners() {
        mBackButton.setOnClickListener {
            navController.popBackStack()
        }
        mUpdateButton.setOnClickListener {
            try {
                viewModel.saveData(
                    User(
                        name = mNameEditText.text.toString(),
                        age = mAgeEditText.text.toString().toLong(),
                        sex = mSexEditText.text.toString(),
                        role = mRoleEditText.text.toString(),
                        about = mAboutEditText.text.toString()
                    )
                )
            } catch (ex: Exception) { //todo:recode this :c
                ex.printStackTrace()
                viewModel.saveData(
                    User(
                        name = mNameEditText.text.toString(),
                        age = 0,
                        sex = mSexEditText.text.toString(),
                        role = mRoleEditText.text.toString(),
                        about = mAboutEditText.text.toString()
                    )
                )
            }
        }
    }

    override fun initNavigation() {
        navController = findNavController()
    }

    override fun initViewModel() {
        viewModel = insertViewModel(viewModelFactory)
        viewModel.userStateLiveData.observe(this){
            handleUserState(it)
        }
        viewModel.userUpdatingStateLiveData.observe(this){
            handleUserUpdatingState(it)
        }
    }

    private fun handleUserState(state: ProfileSettingsViewModel.UserDataState){
        when(state){
            is ProfileSettingsViewModel.UserDataState.Empty -> {
                viewModel.loadData()
                _log("UserDataState: Empty")
            }
            is ProfileSettingsViewModel.UserDataState.Failed -> {
                //todo:handle this
                _log("UserDataState: Failed")
            }
            is ProfileSettingsViewModel.UserDataState.Success -> {
                mNameEditText.setText(state.user.name)
                mAgeEditText.setText(state.user.age.toString())
                mSexEditText.setText(state.user.sex)
                mRoleEditText.setText(state.user.role)
                mAboutEditText.setText(state.user.about)
                _log("UserDataState: Success")
            }
        }
    }

    private fun handleUserUpdatingState(state: ProfileSettingsViewModel.UserUpdatingState){
        when(state){
            is ProfileSettingsViewModel.UserUpdatingState.Empty -> {
                //todo:handle this
                _log("UserUpdatingState: Empty")
            }
            is ProfileSettingsViewModel.UserUpdatingState.Failed -> {
                //todo:handle this
                _log("UserUpdatingState: Failed")
            }
            is ProfileSettingsViewModel.UserUpdatingState.Success -> {
                _log("UserUpdatingState: Success")
                viewModel.userStateLiveData.postValue(ProfileSettingsViewModel.UserDataState.Empty)
                viewModel.userUpdatingStateLiveData.postValue(ProfileSettingsViewModel.UserUpdatingState.Empty)
                navController.navigate(R.id.action_profileSettingsFragment_to_profileFragment)
            }
        }
    }

}