package com.devcraft.currencyassistant

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.devcraft.currencyassistant.data.remote.PostServiceImpl
import com.devcraft.currencyassistant.viewModels.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.ktor.client.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    //private var vm = PostViewModel(PostServiceImpl(HttpClient()))

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        //vm = ViewModelProvider(this)[PostViewModel::class.java]
    }
}