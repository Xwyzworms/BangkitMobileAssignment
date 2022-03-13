package com.example.submission2_ezpz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.submission2_ezpz.adapters.SectionPagerAdapter
import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.data.UserOwner
import com.example.submission2_ezpz.databinding.FragmentDetailUserBinding
import com.example.submission2_ezpz.viewmodels.DetailUserViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserFragment : Fragment() {

    private var _binding : FragmentDetailUserBinding? = null
    private val binding : FragmentDetailUserBinding get() = _binding!!
    private lateinit var sectionPagerAdapter : SectionPagerAdapter
    private lateinit var detailUserViewModel : DetailUserViewModel

    private var user : User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailUserBinding.inflate(inflater)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                try {
                    val currentNavigationLocation = view?.findNavController()?.currentDestination

                    when (currentNavigationLocation) {

                        findNavController()
                            .findDestination(R.id.detailUserFragment) -> findNavController()
                            .navigate(R.id.action_detailUserFragment_to_homeFragment)

                        findNavController().findDestination(R.id.detailUserFragment2) ->
                            findNavController().navigate(R.id.action_detailUserFragment2_to_homeFragment)
                    }
                }
                catch (e: IllegalStateException )  {
                    view?.findNavController()?.navigate(R.id.homeFragment)
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        user = arguments?.getParcelable(DetailUserViewModel.EXTRA_USER)
        settingUpPagerAdapter()
        settingUpViewModels()

    }


    private fun settingUpViewModels() {
        detailUserViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory())[DetailUserViewModel::class.java]
        detailUserViewModel.getUserInformation(user?.username.toString())
        detailUserViewModel.user.observe(viewLifecycleOwner) {
            settingUpUi(it)
        }
        detailUserViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun settingUpUi(user : UserOwner) {
        Glide.with(requireActivity())
            .load(user.avatarUrl)
            .into(binding.ivDuUserPict)
        binding.tvDuName.text = user.name
        if (user.bio?.isEmpty() != true) {
            binding.tvDuBioContent.text = user.bio
        }
        binding.tvDuCompany.text = user.company
        binding.tvDuLocation.text = user.location
        binding.tvDuRepositories.text = user.publicRepos.toString()
        binding.tvDuUsername.text = user.login
        binding.tvDuFoll.text =getString(R.string.follow_info,user.followers,user.following)
    }

    private fun showLoading(state : Boolean) {
        if(state) {
            binding.pbDu.visibility = View.VISIBLE
        }
        else {
            binding.pbDu.visibility = View.INVISIBLE
        }
    }
    private fun settingUpPagerAdapter () {
        sectionPagerAdapter = SectionPagerAdapter(this,user as User)
        binding.viewPagerDu.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabsDu, binding.viewPagerDu) {tab, position ->
            tab.text = resources.getString(DetailUserViewModel.TAB_TITLES[position])
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}