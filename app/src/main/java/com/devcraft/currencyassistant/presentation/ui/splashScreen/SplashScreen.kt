package com.devcraft.currencyassistant.presentation.ui.splashScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devcraft.currencyassistant.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    //private val vm by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //vm.insertData()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}