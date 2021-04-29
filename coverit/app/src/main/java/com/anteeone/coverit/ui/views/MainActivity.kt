package com.anteeone.coverit.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anteeone.coverit.R
import com.anteeone.coverit.ui.utils.extensions.setStatusBarTransparent

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setStatusBarTransparent()
    }
}