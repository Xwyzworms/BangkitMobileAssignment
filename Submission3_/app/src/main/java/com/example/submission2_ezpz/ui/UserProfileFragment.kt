package com.example.submission2_ezpz.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submission2_ezpz.R
import com.example.submission2_ezpz.adapters.SectionPagerAdapter
import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.data.UserOwner
import com.example.submission2_ezpz.databinding.FragmentUserProfileBinding
import com.example.submission2_ezpz.source_data.local.entity.UserEntity
import com.example.submission2_ezpz.viewmodels.UserProfileViewModel
import com.google.android.material.tabs.TabLayoutMediator
class UserProfileFragment : Fragment() {

    private var _binding : FragmentUserProfileBinding? =null
    private val binding : FragmentUserProfileBinding get() = _binding!!


    private lateinit var user : UserEntity
    private lateinit var viewModelUserProfile : UserProfileViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun settingUpTabAdapter() {
        user = UserEntity(
            resources.getString(R.string.dummy_user),
            resources.getString(R.string.dummy_url),
            resources.getString(R.string.dummy_github_url),
            false
        )
        val sectionPagerAdapter = SectionPagerAdapter(this,user)
        settingUpViewHolder()
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initiate Dummy User ( Using my github profile )
        checkIfFragmentAttached {
                viewModelUserProfile = ViewModelProvider(
                    this,
                    ViewModelProvider.NewInstanceFactory()
                )[UserProfileViewModel::class.java]

                settingUpTabAdapter()
            }
    }

    private fun checkIfFragmentAttached(operation: Fragment.() -> Unit) {
        if (isAdded && context != null) {
            operation(this)
        }
    }
    private fun settingUpUi(user : UserOwner){
        binding.tvNameUser.text = user.name
        binding.tvLocation.text = user.location
        binding.tvUpContentRepo.text = user.publicRepos.toString()
        binding.tvUsername.text = user.login
        binding.tvUpContentBio.text =user.bio
        binding.tvCompany.text = user.company

        if(user.email != null) {
            binding.tvEmailUser.text = user.email
        }
        else {
            binding.tvEmailUser.text = resources.getString(R.string.defaultemailprovided)
        }
        binding.tvUpContentFollowers.text = user.followers.toString()
        binding.tvUpContentFollowing.text = user.following.toString()
        Glide.with(requireActivity()).load(user.avatarUrl).into(binding.ivDetailUser)


    }

    private fun showLoading(state : Boolean) {
        if (state)
        {
            binding.pbUp.visibility = View.VISIBLE
            binding.tvPbState.visibility = View.VISIBLE
        }
        else {
            binding.pbUp.visibility = View.INVISIBLE
            binding.tvPbState.visibility = View.INVISIBLE
        }

    }
    private fun settingUpViewHolder() {
        viewModelUserProfile.getUserData(user.username)
        viewModelUserProfile.userData.observe(viewLifecycleOwner){
            settingUpUi(it)
        }


        viewModelUserProfile.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

    }
    companion object {
        @StringRes
        private val TAB_TITLES : IntArray = intArrayOf(
            R.string.followers,
            R.string.following
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}