package com.anteeone.coverit.ui.views.domain

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.anteeone.coverit.R
import com.anteeone.coverit.ui.adapters.ChartsAdapter
import com.anteeone.coverit.ui.utils.extensions._log
import com.anteeone.coverit.ui.utils.extensions.insertViewModel
import com.anteeone.coverit.ui.viewmodels.domain.ChartsViewModel
import com.anteeone.coverit.ui.views.BaseFragment

class ChartsFragment : BaseFragment() {

    private lateinit var viewModel: ChartsViewModel
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSwipeRefresh: SwipeRefreshLayout
    private lateinit var mBackButton: ImageView
    private lateinit var mAdapter: ChartsAdapter
    private lateinit var mProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        insertDependencies()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_charts, container, false)
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
        mSwipeRefresh = view.findViewById(R.id.srl_charts)
        mProgressBar = view.findViewById(R.id.pb_charts)
        mRecyclerView = view.findViewById(R.id.rv_charts)
        mBackButton = view.findViewById(R.id.fr_charts_btn_back)
        mAdapter = ChartsAdapter {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.url)))
        }.also { mRecyclerView.adapter = it }
    }

    override fun initListeners() {
        mSwipeRefresh.setOnRefreshListener {
            viewModel.loadTracks()
        }
        mBackButton.setOnClickListener {
            navController.popBackStack()
        }
    }

    override fun initNavigation() {
        navController = findNavController()
    }

    override fun initViewModel() {
        viewModel = insertViewModel(viewModelFactory)
        viewModel.tracks.observe(viewLifecycleOwner) {
            if (it.forSubscribers) {
                mSwipeRefresh.isRefreshing = false
                when (it.data) {
                    is ChartsViewModel.TracksState.Empty -> {
                        viewModel.loadTracks()
                    }
                    ChartsViewModel.TracksState.Failure -> {
                    }
                    is ChartsViewModel.TracksState.Success -> {
                        mAdapter.setTrackModels(it.data.data) {
                            hideProgress()
                        }
                    }
                }
            }
        }
    }

    fun hideProgress() {
        mProgressBar.visibility = ProgressBar.INVISIBLE
    }
}