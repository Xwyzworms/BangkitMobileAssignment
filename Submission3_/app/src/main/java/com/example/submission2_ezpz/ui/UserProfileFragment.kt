package com.example.submission2_ezpz.ui
import com.example.submission2_ezpz.source_data.Result

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.submission2_ezpz.R
import com.example.submission2_ezpz.adapters.SectionPagerAdapter
import com.example.submission2_ezpz.data.UserOwner
import com.example.submission2_ezpz.databinding.FragmentUserProfileBinding
import com.example.submission2_ezpz.source_data.local.entity.UserEntity
import com.example.submission2_ezpz.source_data.local.setting_preference.SettingPreferences
import com.example.submission2_ezpz.utils.GeneralUtils
import com.example.submission2_ezpz.utils.ViewModelFactory
import com.example.submission2_ezpz.viewmodels.UserProfileViewModel
import com.google.android.material.tabs.TabLayoutMediator

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
class UserProfileFragment : Fragment() {

    private var _binding : FragmentUserProfileBinding? =null
    private val binding : FragmentUserProfileBinding get() = _binding!!


    private lateinit var user : UserEntity
    private lateinit var viewModelUserProfile : UserProfileViewModel
    private lateinit var themePreferences : SettingPreferences
    private lateinit var factory : ViewModelFactory
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

        factory = ViewModelFactory.getInstance(requireContext(),context?.dataStore as DataStore<Preferences> )
        viewModelUserProfile = ViewModelProvider(this,factory)[UserProfileViewModel::class.java]
        settingUpTabAdapter()
        themePreferences = SettingPreferences.getInstance(requireContext().dataStore)
        viewModelUserProfile.getThemeSettings().observe(viewLifecycleOwner) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.smAll.isChecked = true
            }
            else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.smAll.isChecked = false
            }
        }

        binding.smAll.setOnCheckedChangeListener { _ : CompoundButton, isChecked : Boolean ->
            viewModelUserProfile.saveThemeSettings(isChecked)
        }
        binding.fabDetail.setOnClickListener {
            findNavController().navigate(R.id.action_detailUserFragment_to_favoriteFragment)
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

    private fun settingUpViewHolder() {
        viewModelUserProfile.getUserData(user.username).observe(viewLifecycleOwner){ result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> {
                        binding.pbUp.visibility = View.VISIBLE
                        binding.tvPbState.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.pbUp.visibility = View.INVISIBLE
                        binding.tvPbState.visibility = View.INVISIBLE
                        settingUpUi(result.data)

                    }
                    is Result.Error -> {
                        GeneralUtils.printMessage(requireContext(), result.error)
                    }
                }
            }
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