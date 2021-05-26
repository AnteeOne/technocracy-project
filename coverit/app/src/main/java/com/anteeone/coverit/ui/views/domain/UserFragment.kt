package com.anteeone.coverit.ui.views.domain

import android.content.Intent
import android.net.Uri
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
import com.anteeone.coverit.ui.utils.extensions.loadImage
import com.anteeone.coverit.ui.utils.extensions.openLink
import com.anteeone.coverit.ui.views.BaseFragment

class UserFragment : BaseFragment() {

    private lateinit var mUser: User
    private lateinit var mAvatar: ImageView
    private lateinit var mName: TextView
    private lateinit var mAge: TextView
    private lateinit var mRole: TextView
    private lateinit var mAbout: TextView
    private lateinit var mVideoButton: Button
    private lateinit var mBackButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUser()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        initMembers(view)
        initNavigation()
        initListeners()
        return view
    }

    override fun insertDependencies() {

    }

    override fun initMembers(view: View) {
        mAvatar = view.findViewById(R.id.fr_profile_avatar)
        mName = view.findViewById(R.id.fr_user_text_name)
        mAge = view.findViewById(R.id.fr_user_text_age)
        mRole = view.findViewById(R.id.fr_user_text_role)
        mAbout = view.findViewById(R.id.fr_user_text_about)
        mVideoButton = view.findViewById(R.id.fr_user_btn_video)
        mBackButton = view.findViewById(R.id.fr_user_btn_back)
        setUser()
    }

    private fun setUser(){
        mAvatar.loadImage(mUser.avatarUri)
        mName.text = mUser.name
        mAge.text = mUser.age.toString()
        mRole.text = mUser.role
        mAbout.text = mUser.about
        mVideoButton.setOnClickListener {
            openLink(mUser.link)
        }
    }

    override fun initListeners() {
        mBackButton.setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun initNavigation() {
        navController = findNavController()
    }

    override fun initViewModel() {
    }

    private fun initUser(){
        mUser = requireArguments()["user"] as User
    }
}