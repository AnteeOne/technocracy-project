package com.anteeone.coverit.ui.views.domain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.anteeone.coverit.R
import com.anteeone.coverit.ui.adapters.MatchesAdapter
import com.anteeone.coverit.ui.adapters.UsersAdapter
import com.anteeone.coverit.ui.utils.extensions._log
import com.anteeone.coverit.ui.utils.extensions.insertViewModel
import com.anteeone.coverit.ui.viewmodels.domain.MatchesViewModel
import com.anteeone.coverit.ui.views.BaseFragment

class MatchesFragment : BaseFragment() {

    private lateinit var viewModel: MatchesViewModel

    private lateinit var mMatchesList: RecyclerView
    private lateinit var mBackButton: ImageView

    private val mAdapter = MatchesAdapter{
        val action = MatchesFragmentDirections.actionMatchesFragmentToUserFragment(it)
        navController.navigate(action)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        insertDependencies()
        _log("Matches Fragment has been created!")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_matches, container, false)
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
        mMatchesList = view.findViewById(R.id.rv_matches)
        mMatchesList.adapter = mAdapter
        mBackButton = view.findViewById(R.id.fr_matches_btn_back)
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
        viewModel = insertViewModel(viewModelFactory)
        viewModel.users.observe(viewLifecycleOwner) {
            if (it.forSubscribers) when (it.value()) {
                is MatchesViewModel.UsersState.Empty -> {
                    _log("MatchesFr: Empty")
                    viewModel.loadUsers()
                }
                is MatchesViewModel.UsersState.Failure -> {
                    //todo: handle this
                    viewModel.users.value = MatchesViewModel.UsersState.Empty.pack(false)
                    _log("MatchesFr: Failure")
                }
                is MatchesViewModel.UsersState.Success -> {
                    val users = ((it.data) as MatchesViewModel.UsersState.Success).data
                    mAdapter.setUsers(users)
                    _log("MatchesFr: Success[+${users.size} users]")
                }
            }
        }
    }
}