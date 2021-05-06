package com.anteeone.coverit.ui.views.domain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.anteeone.coverit.R
import com.anteeone.coverit.ui.views.BaseFragment

class ProfileFragment : BaseFragment() {

    private lateinit var mProfileSettingsButton: ImageView

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
        mProfileSettingsButton = view.findViewById(R.id.fr_profile_btn_settings)
    }

    override fun initListeners() {
        mProfileSettingsButton.setOnClickListener{
            navController.navigate(R.id.action_profileFragment_to_profileSettingsFragment)
        }
    }

    override fun initNavigation() {
        navController = findNavController()
    }

    override fun initViewModel() {
//        TODO("Not yet implemented")
    }
}