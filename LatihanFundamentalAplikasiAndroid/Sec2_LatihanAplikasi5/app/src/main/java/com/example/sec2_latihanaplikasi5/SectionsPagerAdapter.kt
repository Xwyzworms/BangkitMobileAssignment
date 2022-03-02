package com.example.sec2_latihanaplikasi5

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity : AppCompatActivity) : FragmentStateAdapter(activity) {



    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        val fragmentHome : Fragment = HomeFragment()

                fragmentHome.arguments = Bundle().apply {
                    putInt(HomeFragment.ARG_SECTION_NUMBER, position +1 )
                }
                return fragmentHome
    }



}