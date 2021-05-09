package com.anteeone.coverit.ui.utils.extensions

import android.app.Activity
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import com.anteeone.coverit.R

fun Activity.setNavigationBarTransparent(){
    getWindow().setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
}

fun Activity.disableNightTheme(){
    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
}

fun Activity.setAuthTheme(){
    setTheme(R.style.Theme_Design_NoActionBar)
}

fun Activity.setUpDesign(){
    setNavigationBarTransparent()
    disableNightTheme()
}