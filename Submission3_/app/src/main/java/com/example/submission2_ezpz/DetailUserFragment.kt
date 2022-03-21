package com.example.submission2_ezpz
import android.content.Context
import com.example.submission2_ezpz.source_data.Result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.submission2_ezpz.adapters.SectionPagerAdapter
import com.example.submission2_ezpz.data.UserOwner
import com.example.submission2_ezpz.databinding.FragmentDetailUserBinding
import com.example.submission2_ezpz.source_data.local.entity.UserEntity
import com.example.submission2_ezpz.utils.GeneralUtils
import com.example.submission2_ezpz.utils.ViewModelFactory
import com.example.submission2_ezpz.viewmodels.DetailUserViewModel
import com.google.android.material.tabs.TabLayoutMediator


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
class DetailUserFragment : Fragment() {

    private var _binding : FragmentDetailUserBinding? = null
    private val binding : FragmentDetailUserBinding get() = _binding!!
    private lateinit var sectionPagerAdapter : SectionPagerAdapter
    private lateinit var detailUserViewModel : DetailUserViewModel
    private lateinit var factory : ViewModelFactory

    private var tempUser : UserEntity? = null
    private lateinit var user : UserEntity
    private var isUserAvailable : Boolean  = false

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

        tempUser = arguments?.getParcelable(DetailUserViewModel.EXTRA_USER)
        if (tempUser!= null) {
            isUserAvailable = true
            user = tempUser as UserEntity
        }
        if (isUserAvailable) {
            setStarred()
            settingUpPagerAdapter()
            binding.ivStarred.setOnClickListener {
                if(user.isFavorite){
                    detailUserViewModel.setUserFavorite(user,false)
                    binding.ivStarred.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_border_24))
                }
                else {
                    detailUserViewModel.setUserFavorite(user,true)
                    binding.ivStarred.setImageDrawable(
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_24)
                    )
                }
            }
            settingUpViewModels()
        }

    }
    private fun setStarred() {
        if (user.isFavorite) {
            binding.ivStarred.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_24)
            )
        }
        else {

            binding.ivStarred.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_border_24))
        }
    }
    private fun settingUpViewModels() {

        factory = ViewModelFactory.getInstance(requireContext(), context?.dataStore as DataStore<Preferences> )
        detailUserViewModel = ViewModelProvider(this,factory).get(DetailUserViewModel::class.java)

        detailUserViewModel.getUserInformation(user.username).observe(viewLifecycleOwner) {result ->
            if(result != null) {
                when(result) {
                    is Result.Loading -> {
                        binding.pbDu.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.pbDu.visibility =View.INVISIBLE
                        settingUpUi(result.data)
                    }
                    is Result.Error -> {
                        GeneralUtils.printMessage(requireContext(), result.error)
                    }
                }
            }
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


    private fun settingUpPagerAdapter () {
        sectionPagerAdapter = SectionPagerAdapter(this,user )
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