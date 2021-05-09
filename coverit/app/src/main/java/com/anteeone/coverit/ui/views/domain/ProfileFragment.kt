package com.anteeone.coverit.ui.views.domain

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.anteeone.coverit.R
import com.anteeone.coverit.domain.models.User
import com.anteeone.coverit.ui.utils.extensions.insertViewModel
import com.anteeone.coverit.ui.utils.extensions.loadImage
import com.anteeone.coverit.ui.viewmodels.domain.ProfileViewModel
import com.anteeone.coverit.ui.views.BaseFragment
import com.anteeone.coverit.ui.views.auth.AuthActivity

class ProfileFragment : BaseFragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var mProfileSettingsButton: ImageView
    private lateinit var mLogoutButton: ImageView
    private lateinit var mAvatar: ImageView
    private lateinit var mName: TextView
    private lateinit var mAge: TextView
    private lateinit var mRole: TextView
    private lateinit var mAbout: TextView
    private lateinit var mVideoButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        insertDependencies()
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        initMembers(view)
        initNavigation()
        initListeners()
        return view
    }

    override fun insertDependencies() {
        appComponent.inject(this)
    }

    override fun initMembers(view: View) {
        mProfileSettingsButton = view.findViewById(R.id.fr_profile_btn_change)
        mLogoutButton = view.findViewById(R.id.fr_profile_btn_logout)
        mAvatar = view.findViewById(R.id.fr_profile_avatar)
        mName = view.findViewById(R.id.fr_profile_text_name)
        mAge = view.findViewById(R.id.fr_profile_text_age)
        mRole = view.findViewById(R.id.fr_profile_text_role)
        mAbout = view.findViewById(R.id.fr_profile_text_about)
        mVideoButton = view.findViewById(R.id.fr_profile_btn_video)
    }

    override fun initListeners() {
        mProfileSettingsButton.setOnClickListener{
            navController.navigate(R.id.action_profileFragment_to_profileSettingsFragment)
        }
        mLogoutButton.setOnClickListener {
            viewModel.signOut()
            val logoutIntent = Intent(context,AuthActivity::class.java)
            activity?.finish()
            startActivity(logoutIntent)
        }
    }

    override fun initNavigation() {
        navController = findNavController()
    }

    override fun initViewModel() {
        viewModel = insertViewModel(viewModelFactory)
        viewModel.userState.observe(this){
            if(it.forSubscribers) when(it.data){
                is ProfileViewModel.UserState.Empty -> {
                    viewModel.loadUser()
                }
                is ProfileViewModel.UserState.Failure -> {
                    viewModel.userState.postValue(ProfileViewModel.UserState.Empty.pack(false))
                }
                is ProfileViewModel.UserState.Success -> {
                    val user = it.data.data
                    setUser(user)
                }
            }
        }
    }

    private fun setUser(user: User) {
        mAvatar.loadImage(user.avatarUri)
        mName.text = user.name
        mAge.text = user.age.toString()
        mRole.text = user.role
        mAbout.text = user.about
        mVideoButton.setOnClickListener {
            //todo
        }
    }
}