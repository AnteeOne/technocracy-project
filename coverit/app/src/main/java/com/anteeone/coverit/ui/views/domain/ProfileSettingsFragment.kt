package com.anteeone.coverit.ui.views.domain

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.Image
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
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
import com.anteeone.coverit.ui.utils.extensions.loadImage
import com.anteeone.coverit.ui.viewmodels.domain.ProfileSettingsViewModel
import com.anteeone.coverit.ui.views.BaseFragment


class ProfileSettingsFragment : BaseFragment() {

    private lateinit var viewModel: ProfileSettingsViewModel

    private lateinit var mBackButton: ImageView
    private lateinit var mPhotoButton: ImageView
    private lateinit var mUpdateButton: Button
    private lateinit var mNameEditText: EditText
    private lateinit var mAgeEditText: EditText
    private lateinit var mSexEditText: EditText
    private lateinit var mRoleEditText: EditText
    private lateinit var mAboutEditText: EditText
    private lateinit var mAvatar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        insertDependencies()
        _log("ProfileSettingsFragment has been created!")
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_settings, container, false)
        initViewModel()
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
        mPhotoButton = view.findViewById(R.id.fr_profile_settings_btn_change_photo)
        mNameEditText = view.findViewById(R.id.fr_profile_settings_et_name)
        mAgeEditText = view.findViewById(R.id.fr_profile_settings_et_age)
        mSexEditText = view.findViewById(R.id.fr_profile_settings_et_sex)
        mRoleEditText = view.findViewById(R.id.fr_profile_settings_et_role)
        mAboutEditText = view.findViewById(R.id.fr_profile_settings_et_about)
        mAvatar = view.findViewById(R.id.fr_profile_settings_avatar)
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
        mPhotoButton.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1000)
        }
    }

    override fun initNavigation() {
        navController = findNavController()
    }

    override fun initViewModel() {
        viewModel = insertViewModel(viewModelFactory)
        viewModel.userStateLiveData.observe(viewLifecycleOwner) {
            handleUserState(it)
        }
        viewModel.userUpdatingStateLiveData.observe(viewLifecycleOwner) {
            handleUserUpdatingState(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        captureImage(requestCode, resultCode, data)
    }

    private fun handleUserState(state: ProfileSettingsViewModel.UserDataState) {
        when (state) {
            is ProfileSettingsViewModel.UserDataState.Empty -> {
                viewModel.loadData()
            }
            is ProfileSettingsViewModel.UserDataState.Failed -> {
                //todo:handle this
            }
            is ProfileSettingsViewModel.UserDataState.Success -> {
                mNameEditText.setText(state.user.name)
                mAgeEditText.setText(state.user.age.toString())
                mSexEditText.setText(state.user.sex)
                mRoleEditText.setText(state.user.role)
                mAboutEditText.setText(state.user.about)
                mAvatar.loadImage(state.user.avatarUri)
            }
        }
    }

    private fun handleUserUpdatingState(state: ProfileSettingsViewModel.UserUpdatingState) {
        when (state) {
            is ProfileSettingsViewModel.UserUpdatingState.Empty -> {
                //todo:handle this
            }
            is ProfileSettingsViewModel.UserUpdatingState.Failed -> {
                //todo:handle this
            }
            is ProfileSettingsViewModel.UserUpdatingState.Success -> {
                viewModel.userStateLiveData.postValue(ProfileSettingsViewModel.UserDataState.Empty)
                viewModel.userUpdatingStateLiveData.postValue(ProfileSettingsViewModel.UserUpdatingState.Empty)
                navController.navigate(R.id.action_profileSettingsFragment_to_profileFragment)
            }
        }
    }

    private fun captureImage(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK && requestCode == 1000) {
            val uri: Uri = data?.data as Uri
            var bitmapImage: Bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(requireActivity().contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }
            viewModel.saveImage(bitmapImage)
            mAvatar.setImageBitmap(bitmapImage)

        }
    }
}