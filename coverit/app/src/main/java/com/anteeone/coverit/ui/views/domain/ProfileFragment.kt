package com.anteeone.coverit.ui.views.domain

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.anteeone.coverit.R
import com.anteeone.coverit.ui.views.BaseFragment
import com.anteeone.coverit.ui.views.auth.AuthActivity

class ProfileFragment : BaseFragment() {

    private lateinit var mProfileSettingsButton: ImageView
    private lateinit var mLogoutButton: ImageView

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
//        TODO("Not yet implemented")
    }

    override fun initMembers(view: View) {
        mProfileSettingsButton = view.findViewById(R.id.fr_user_btn_back)
        mLogoutButton = view.findViewById(R.id.fr_profile_btn_logout)
    }

    override fun initListeners() {
        mProfileSettingsButton.setOnClickListener{
            navController.navigate(R.id.action_profileFragment_to_profileSettingsFragment)
        }
        mLogoutButton.setOnClickListener {
            val logoutIntent = Intent(context,AuthActivity::class.java)
            activity?.finish()
            startActivity(logoutIntent)
            //todo: firebase auth 'session' cleaning
        }
    }

    override fun initNavigation() {
        navController = findNavController()
    }

    override fun initViewModel() {
//        TODO("Not yet implemented")
    }
}