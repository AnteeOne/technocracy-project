package com.anteeone.coverit.ui.views.domain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.anteeone.coverit.R
import com.anteeone.coverit.ui.adapters.ChatDetailAdapter
import com.anteeone.coverit.ui.utils.extensions._log
import com.anteeone.coverit.ui.utils.extensions.insertViewModel
import com.anteeone.coverit.ui.viewmodels.domain.ChatDetailsViewModel
import com.anteeone.coverit.ui.views.BaseFragment

private const val ARG_PARAM1 = "receiverId"

class ChatDetailsFragment : BaseFragment() {
    private var param1: String? = null

    private lateinit var viewModel: ChatDetailsViewModel

    private lateinit var mBackButton: ImageView
    private lateinit var mSendButton: ImageView
    private lateinit var mEditText: EditText
    private lateinit var mProgressBar: ProgressBar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: ChatDetailAdapter
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        insertDependencies()
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chat_details, container, false)
        initViewModel()
        initMembers(view)
        initNavigation()
        initListeners()
        loadUsers()
        return view
    }

    override fun insertDependencies() {
        appComponent.inject(this)
    }

    override fun initMembers(view: View) {
        mBackButton = view.findViewById(R.id.fragment_chat_details_btn_back)
        mSendButton = view.findViewById(R.id.fragment_chat_details_btn_send)
        mEditText = view.findViewById(R.id.fragment_chat_details_et)
        mProgressBar = view.findViewById(R.id.pb_chat_details)
        mSwipeRefreshLayout = view.findViewById(R.id.srl_chat_details)
        mAdapter = ChatDetailAdapter()
        mRecyclerView = view.findViewById(R.id.rv_chat_details)
        mRecyclerView.adapter = mAdapter
    }

    override fun initListeners() {
        mBackButton.setOnClickListener {
            navController.popBackStack()
        }
        mSendButton.setOnClickListener {
            mProgressBar.visibility = ProgressBar.VISIBLE
            viewModel.addMessage(mEditText.text.toString(), param1!!)
        }
        mSwipeRefreshLayout.setOnRefreshListener {
            viewModel.loadMessages(param1!!)
        }
    }

    override fun initNavigation() {
        navController = findNavController()
    }

    override fun initViewModel() {
        viewModel = insertViewModel(viewModelFactory)
        viewModel.messagesStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                ChatDetailsViewModel.MessagesState.Empty -> {
                    _log("messages size = empty")
                }
                ChatDetailsViewModel.MessagesState.Failure -> {
                    hideLoading()
                    _log("messages size = failure")
                }
                is ChatDetailsViewModel.MessagesState.Success -> {
                    _log("messages size = ${it.data.size}")
                    mRecyclerView.scrollToPosition(mAdapter.itemCount)
                    mAdapter.setData(it.data){
                        hideLoading()
                    }

                }
            }
        }
        viewModel.userIdLiveData.observe(viewLifecycleOwner) {
            if (it != null && it.isNotEmpty()) mAdapter.setCurrentUser(it)
        }
    }

    private fun loadUsers() {
        showLoading()
        viewModel.loadMessages(param1!!)

    }

    private fun showLoading() {
        mProgressBar.visibility = ProgressBar.VISIBLE
        mRecyclerView.visibility = RecyclerView.GONE
    }

    private fun hideLoading() {
        mProgressBar.visibility = ProgressBar.GONE
        mRecyclerView.visibility = RecyclerView.VISIBLE
        mSwipeRefreshLayout.isRefreshing = false
    }

}