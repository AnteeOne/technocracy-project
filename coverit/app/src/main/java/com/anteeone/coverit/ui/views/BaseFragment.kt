package com.anteeone.coverit.ui.views

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.anteeone.coverit.App
import com.anteeone.coverit.di.components.AppComponent
import com.anteeone.coverit.ui.utils.extensions.showToast
import javax.inject.Inject

open abstract class BaseFragment: Fragment() {

    protected lateinit var navController: NavController

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE){
        (activity?.application as App).appComponent
    }

    protected abstract fun insertDependencies()

    protected abstract fun initMembers(view: View)

    protected abstract fun initListeners()

    protected abstract fun initNavigation()

    protected abstract fun initViewModel()

    protected fun showToast(message: String){
        context?.let { showToast(it, message) }
    }

//    protected fun insertDependencies(){
//        appComponent.inject(this)
//    }
// todo: try to inject abstract class

}