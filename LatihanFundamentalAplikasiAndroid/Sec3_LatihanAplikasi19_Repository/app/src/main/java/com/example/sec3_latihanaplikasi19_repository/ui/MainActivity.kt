package com.example.sec3_latihanaplikasi19_repository.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import com.example.sec3_latihanaplikasi19_repository.R
import com.example.sec3_latihanaplikasi19_repository.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionPagerAdapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager) {
            tab : TabLayout.Tab, position : Int ->
                tab.text =  resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.home, R.string.bookmark)
    }
}