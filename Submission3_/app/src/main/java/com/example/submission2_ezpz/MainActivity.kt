package com.example.submission2_ezpz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.submission2_ezpz.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navController : NavController
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView : BottomNavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_activity_main)

        navController.addOnDestinationChangedListener {_, destination, _ ->
            when(destination.id) {
             R.id.favoriteFragment -> hideBottomNav()
             R.id.detailUserFragment2 -> hideBottomNav()
                else -> {
                    showBottomNav()
                }

            }
        }
        val appBarConfiguration = AppBarConfiguration.Builder(
            setOf(R.id.homeFragment, R.id.exploreFragment, R.id.detailUserFragment)
        ).build()
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }

    private fun hideBottomNav() {
        binding.navView.visibility = View.INVISIBLE
    }
    private fun showBottomNav() {
        binding.navView.visibility = View.VISIBLE

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}