package com.anteeone.coverit.ui.views

import androidx.appcompat.app.AppCompatActivity
import com.anteeone.coverit.App
import com.anteeone.coverit.di.components.AppComponent

open class BaseActivity:AppCompatActivity() {

    val appComponent: AppComponent by lazy(mode = LazyThreadSafetyMode.NONE){
        (application as App).appComponent
    }

}