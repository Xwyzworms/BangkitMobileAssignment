package com.example.latfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG : String = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val mFragmentManager  : FragmentManager = supportFragmentManager
        val mHomeFragment :  HomeFragment = HomeFragment()
        val fragment = mFragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)

        if (fragment !is HomeFragment) {
            Log.d(TAG, "Fragment Name ${HomeFragment::class.java.simpleName}")
            mFragmentManager.beginTransaction().apply {
                add(R.id.frame_container, mHomeFragment ,HomeFragment::class.java.simpleName )
            }.commit()
        }



    }
}