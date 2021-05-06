package com.anteeone.coverit.ui.views.auth

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION
import android.os.Bundle
import android.util.Log
import com.anteeone.coverit.R
import com.anteeone.coverit.domain.usecases.auth.CheckUserAuthUsecase
import com.anteeone.coverit.domain.utils.Outcome
import com.anteeone.coverit.ui.utils.extensions.setAuthTheme
import com.anteeone.coverit.ui.utils.extensions.setUpDesign
import com.anteeone.coverit.ui.views.domain.MainActivity
import com.anteeone.coverit.ui.views.BaseActivity
import javax.inject.Inject

class AuthActivity  : BaseActivity() {

    @Inject
    lateinit var checkUserAuthUsecase: CheckUserAuthUsecase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
        tryToRedirectToHome()
        setAuthTheme()  //todo:fix this fkn theme bug :_(
        setContentView(R.layout.activity_auth)
        setUpDesign()
    }

    private fun tryToRedirectToHome(){
        when(checkUserAuthUsecase.getCurrentUser()){
            is Outcome.Success -> {
                val intent = Intent(this, MainActivity::class.java).also{
                    it.flags = FLAG_ACTIVITY_NO_ANIMATION
                }
                finish()
                overridePendingTransition(0,0)
                startActivity(intent)
            }
            is Outcome.Failure -> {
                Log.println(Log.INFO,"coverit-tag","User hasn't logged in")
            }
        }
    }

    private fun injectDependencies(){
        appComponent.inject(this)
    }
}