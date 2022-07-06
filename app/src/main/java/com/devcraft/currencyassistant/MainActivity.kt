package com.devcraft.currencyassistant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.devcraft.currencyassistant.app.OnBackPressed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun getCurrentFragment(): Fragment? {
        return supportFragmentManager
            .findFragmentById(R.id.main_fragment)
            ?.childFragmentManager
            ?.fragments
            ?.get(0)
    }

    override fun onBackPressed() {
        tellFragments()
    }

    private fun tellFragments() {
        val fragment = getCurrentFragment() ?: return
        if (fragment is OnBackPressed) {
            fragment.onBackPressed()
        }
    }
}