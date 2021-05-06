package com.anteeone.coverit.ui.utils.extensions

import android.util.Log

private val APP_TAG = "coverit-tag"

fun Any._log(message: String) {
    Log.println(Log.INFO, APP_TAG, message)
}