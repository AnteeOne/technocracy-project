package com.anteeone.coverit.ui.views.domain

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.anteeone.coverit.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var mBottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CoverIt)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        initMembers()
        val host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        initListeners(host.navController)
        //todo: refactor this
    }

    private fun initMembers(){
        mBottomNavigation = findViewById(R.id.bottom_navigation)
    }

    private fun initListeners(navController: NavController){
        mBottomNavigation.setupWithNavController(navController)
        setupNavigation(navController)
    }

    private fun setupNavigation(navController: NavController){
        navController.addOnDestinationChangedListener{_,destination,_ ->
            when(destination.id){
                R.id.homeFragment , R.id.chatFragment, R.id.profileFragment -> showBottomNavigation()
                else -> hideBottomNavigation()
            }
        }
    }

    private fun showBottomNavigation(){
        mBottomNavigation.visibility = View.VISIBLE
    }

    private fun hideBottomNavigation(){
        mBottomNavigation.visibility = View.GONE
    }
}