package com.anteeone.coverit.ui.views.domain

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.anteeone.coverit.R
import com.anteeone.coverit.ui.adapters.UsersAdapter
import com.anteeone.coverit.ui.utils.extensions.insertViewModel
import com.anteeone.coverit.ui.viewmodels.domain.HomeViewModel
import com.anteeone.coverit.ui.views.BaseFragment
import com.yuyakaido.android.cardstackview.*


class HomeFragment : BaseFragment(), CardStackListener {

    private lateinit var viewModel: HomeViewModel;

    private lateinit var mCardStackView: CardStackView
    private lateinit var arrayAdapter: ArrayAdapter<String>
    private val mAdapter = UsersAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        insertDependencies()
        initViewModel()
        viewModel.loadUsersList()
        Log.println(Log.INFO, "coverit-tag", "HomeFragment has been created")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        initMembers(view)
        initNavigation()
        initListeners()
        return view
    }

    override fun insertDependencies() {
        appComponent.inject(this)
    }

    override fun initMembers(view: View) {
        mCardStackView = view.findViewById(R.id.fr_home_fling_container)
        mCardStackView.also {
            it.adapter = mAdapter
            it.layoutManager = CardStackLayoutManager(context, this).also {
                it.setStackFrom(StackFrom.Top)
            }
        }
    }

    override fun initListeners() {

    }

    override fun initNavigation() {
        navController = findNavController()
    }

    override fun initViewModel() {
        viewModel = insertViewModel(viewModelFactory)

        viewModel.users.observe(this) { users ->
            if (users.isNotEmpty()) {
                Log.println(Log.INFO, "coverit-tag", "${users.size} new users have been loaded!")
                mAdapter.setUsers(users)
            }

        }
    }

    override fun onCardDragging(direction: Direction, ratio: Float) {}

    override fun onCardSwiped(direction: Direction) {
        when (direction) {
            Direction.Left -> {
                viewModel.dislikeUser()
            }
            Direction.Right -> {
                viewModel.likeUser()
            }
            else -> {
            }
        }
    }

    override fun onCardRewound() {
//        TODO("Not yet implemented")
    }

    override fun onCardCanceled() {
//        TODO("Not yet implemented")
    }

    override fun onCardAppeared(view: View, position: Int) {
//        TODO("Not yet implemented")
    }

    override fun onCardDisappeared(view: View, position: Int) {
//        TODO("Not yet implemented")
    }
}