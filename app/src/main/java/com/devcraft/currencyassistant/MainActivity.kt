package com.devcraft.currencyassistant

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.devcraft.currencyassistant.utils.status.OnBackPressed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onBackPressed() {
        val currentFragment: Fragment = supportFragmentManager.fragments[0] ?: return
        val controller = Navigation.findNavController(this, R.id.main_fragment)
        if (currentFragment is OnBackPressed) (currentFragment as OnBackPressed).onBackPressed()
        else if (!controller.popBackStack()) finish()
    }
}