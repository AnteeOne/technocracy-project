package com.anteeone.coverit.ui.utils.extensions

import android.app.Activity
import android.view.WindowManager

fun Activity.setStatusBarTransparent(){
    getWindow().setFlags(
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
}