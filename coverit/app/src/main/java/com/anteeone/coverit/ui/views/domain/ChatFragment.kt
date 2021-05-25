package com.anteeone.coverit.ui.views.domain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.anteeone.coverit.R
import com.anteeone.coverit.ui.adapters.MatchesAdapter
import com.anteeone.coverit.ui.utils.extensions._log
import com.anteeone.coverit.ui.utils.extensions.insertViewModel
import com.anteeone.coverit.ui.viewmodels.domain.MatchesViewModel
import com.anteeone.coverit.ui.views.BaseFragment


class ChatFragment : BaseFragment() {

    private lateinit var viewModel: MatchesViewModel

    private lateinit var mChatList: RecyclerView
    private lateinit var mBackButton: ImageView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mProgressBar: ProgressBar

    private val mAdapter = MatchesAdapter{
        val action = ChatFragmentDirections.actionChatFragmentToChatDetailsFragment(it.id)
        navController.navigate(action)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        insertDependencies()
        _log("Chat Fragment has been created!")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
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
        mChatList = view.findViewById(R.id.rv_chat)
        mChatList.adapter = mAdapter
        mSwipeRefreshLayout = view.findViewById(R.id.srl_chat)
        mProgressBar = view.findViewById(R.id.pb_chat)
    }

    override fun initListeners() {
        mSwipeRefreshLayout.setOnRefreshListener {
            viewModel.loadUsers()
        }
    }

    override fun initNavigation() {
        navController = findNavController()
    }

    override fun initViewModel() {
        viewModel = insertViewModel(viewModelFactory)
        viewModel.users.observe(viewLifecycleOwner) {
            if (it.forSubscribers) {
                mSwipeRefreshLayout.isRefreshing = false
                when (it.value()) {
                    is MatchesViewModel.UsersState.Empty -> {
                        viewModel.loadUsers()
                    }
                    is MatchesViewModel.UsersState.Failure -> {
                        //todo: handle this
                        viewModel.users.value = MatchesViewModel.UsersState.Empty.pack(false)
                    }
                    is MatchesViewModel.UsersState.Success -> {
                        val users = ((it.data) as MatchesViewModel.UsersState.Success).data
                        mAdapter.setUsers(users){
                            mProgressBar.visibility = ProgressBar.INVISIBLE
                        }
                    }
                }
            }
        }
    }
}