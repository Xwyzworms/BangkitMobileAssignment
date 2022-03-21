package com.example.submission2_ezpz.ui

import android.content.Context
import com.example.submission2_ezpz.utils.GeneralUtils
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2_ezpz.R
import com.example.submission2_ezpz.adapters.DetailUserAdapter
import com.example.submission2_ezpz.adapters.SectionPagerAdapter
import com.example.submission2_ezpz.databinding.FragmentFollowingBinding
import com.example.submission2_ezpz.source_data.local.entity.UserEntity
import com.example.submission2_ezpz.utils.ViewModelFactory
import com.example.submission2_ezpz.viewmodels.DetailUserViewModel
import com.example.submission2_ezpz.viewmodels.FollowingViewModel

import com.example.submission2_ezpz.source_data.Result


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
class FollowingFragment : Fragment() {

    private var _binding : FragmentFollowingBinding? = null
    private val binding : FragmentFollowingBinding get() = _binding!!

    private lateinit var itemListener :DetailUserAdapter.OnItemDuClicked
    private lateinit var user : UserEntity
    private lateinit var viewModelFollowingFragment : FollowingViewModel
    private lateinit var factory : ViewModelFactory

    //Variable Utility
    private var tempUser : UserEntity? =null
    private var isUserAvailable : Boolean = false
    // ================================= VARIABLE SECTION ====================================

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        factory = ViewModelFactory.getInstance(requireContext(), context?.dataStore as DataStore<Preferences>)
        viewModelFollowingFragment = ViewModelProvider(this, factory).get(FollowingViewModel::class.java)
        tempUser = arguments?.getParcelable(SectionPagerAdapter.ARG_NAME)
        if ( tempUser != null ) {
            isUserAvailable = true
        }
        if( isUserAvailable) {
            user = tempUser as UserEntity
            itemListener = object : DetailUserAdapter.OnItemDuClicked {
                override fun onItemClicked(user: UserEntity) {
                    goToDetails(user)
                }
                override fun onGithubClicked(github_url: String) {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(github_url)
                    requireActivity().startActivity(intent)
                }
                override fun onFavoriteClicked(user: UserEntity) {
                    if (user.isFavorite) viewModelFollowingFragment.setFavorite(user,false)
                    else viewModelFollowingFragment.setFavorite(user,true)
                }
            }
            settingUpViewModel()
            val linearLayout = LinearLayoutManager(requireActivity())
            binding.rvFollowing.layoutManager = linearLayout
            binding.rvFollowing.setHasFixedSize(true)
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun goToDetails(user: UserEntity) {
        val mBundle = Bundle()
        mBundle.putParcelable(DetailUserViewModel.EXTRA_USER, user)
        val currentNavigationLocation = findNavController().currentDestination
        when {
                currentNavigationLocation == findNavController()
                    .findDestination(R.id.detailUserFragment) -> findNavController()
                    .navigate(R.id.action_detailUserFragment_to_detailUserFragment2, mBundle)

            currentNavigationLocation == findNavController().findDestination(R.id.detailUserFragment2) ->
                findNavController().navigate(R.id.action_detailUserFragment2_self,mBundle)

        }
    }

    private fun settingUpViewModel() {

        viewModelFollowingFragment.getFollowing(user.username).observe(viewLifecycleOwner) {result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> {
                        binding.pbFollowing.visibility = View.VISIBLE
                        binding.tvFollowingTextTitle.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.pbFollowing.visibility = View.INVISIBLE
                        binding.tvFollowingTextTitle.visibility = View.INVISIBLE
                        val adapter = DetailUserAdapter(result.data)
                        adapter.setOnItemDuClickedListener(itemListener)
                        adapter.disabledFavorite = true
                        binding.rvFollowing.adapter = adapter
                    }
                    is Result.Error -> {
                        GeneralUtils.printMessage(requireContext(), result.error)
                    }
                }
            }
        }

    }

}