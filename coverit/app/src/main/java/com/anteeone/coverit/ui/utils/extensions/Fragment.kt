package com.anteeone.coverit.ui.utils.extensions

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun Fragment.showToast(context: Context,message: String){
    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
}

inline fun <reified T : ViewModel> Fragment.insertViewModel(factory: ViewModelProvider.Factory) =
    ViewModelProvider(requireActivity(),factory).get(T::class.java)