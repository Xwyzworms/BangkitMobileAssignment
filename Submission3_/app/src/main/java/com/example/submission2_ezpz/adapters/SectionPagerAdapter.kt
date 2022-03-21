package com.example.submission2_ezpz.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.source_data.local.entity.UserEntity
import com.example.submission2_ezpz.ui.FollowersFragment
import com.example.submission2_ezpz.ui.FollowingFragment

class SectionPagerAdapter(fragment : Fragment,dataUserParams : UserEntity) : FragmentStateAdapter(fragment){
    private var dataUser : UserEntity = dataUserParams
    override fun getItemCount(): Int {
        return totalPage
    }

    override fun createFragment(position: Int): Fragment {
        var fragment : Fragment?  =null
        val args = Bundle().apply {
            putParcelable(ARG_NAME,dataUser)
        }
        when(position) {
            0 -> {
                fragment = FollowersFragment()
                fragment.arguments = args
            }
            1 -> {
                fragment = FollowingFragment()
                fragment.arguments = args
            }
        }
        return fragment as Fragment
    }

    companion object {
        const val ARG_NAME : String = "data_user"
        private const val totalPage : Int = 2
    }
}