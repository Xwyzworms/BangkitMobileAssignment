package com.example.submission2_ezpz.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2_ezpz.R
import com.example.submission2_ezpz.adapters.DetailUserAdapter
import com.example.submission2_ezpz.adapters.SectionPagerAdapter
import com.example.submission2_ezpz.databinding.FragmentFollowersBinding
import com.example.submission2_ezpz.source_data.Result
import com.example.submission2_ezpz.source_data.local.entity.UserEntity
import com.example.submission2_ezpz.source_data.repository.UserRepository
import com.example.submission2_ezpz.utils.GeneralUtils
import com.example.submission2_ezpz.utils.ViewModelFactory
import com.example.submission2_ezpz.viewmodels.DetailUserViewModel
import com.example.submission2_ezpz.viewmodels.FollowersViewModel

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
class FollowersFragment : Fragment(), DetailUserAdapter.OnItemDuClicked {

    private  var _binding : FragmentFollowersBinding? =null
    private val binding : FragmentFollowersBinding get() = _binding!!
    private lateinit var viewModelFollowersFragment : FollowersViewModel
    private lateinit var user : UserEntity
    private var tempUser : UserEntity? = null
    private var userAvailable : Boolean = false
    private lateinit var factory : ViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tempUser = arguments?.getParcelable(SectionPagerAdapter.ARG_NAME)

        if (tempUser != null) {
            user = tempUser as UserEntity
            userAvailable = true
        }

        if (userAvailable) {
            val linearLayout = LinearLayoutManager(requireActivity())
            factory = ViewModelFactory.getInstance(requireContext(), context?.dataStore as DataStore<Preferences>)
            viewModelFollowersFragment = ViewModelProvider( requireActivity(), factory).get(FollowersViewModel::class.java)
            binding.rvFollowers.layoutManager = linearLayout
            binding.rvFollowers.setHasFixedSize(true)

            settingUpViewModel()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun settingUpRv(list_Data : List<UserEntity>) {
        val adapter = DetailUserAdapter(list_Data)
        adapter.disabledFavorite = true
        adapter.setOnItemDuClickedListener(this)
        binding.rvFollowers.adapter = adapter
    }


    private fun settingUpViewModel() {
        viewModelFollowersFragment.getFollowers(user.username).observe(viewLifecycleOwner) {result ->
            if (result  != null) {
                Log.d(TAG, result.toString())
                when (result)  {
                    is Result.Loading -> {
                        binding.pbFollowers.visibility = View.VISIBLE
                        binding.tvFollowersTextTitle.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.pbFollowers.visibility = View.INVISIBLE
                        binding.tvFollowersTextTitle.visibility = View.INVISIBLE
                        binding.rvFollowers.visibility = View.VISIBLE
                        settingUpRv(result.data)
                    }
                    is Result.Error -> {
                        GeneralUtils.printMessage(requireContext(), result.error)
                    }
                }
            }
        }


    }

    private fun goToDetails(user : UserEntity) {
        val mBundle = Bundle()
        mBundle.putParcelable(DetailUserViewModel.EXTRA_USER, user)
        val currentNavigationLocation = findNavController().currentDestination

        when (currentNavigationLocation) {
             findNavController()
                .findDestination(R.id.detailUserFragment) -> findNavController()
                .navigate(R.id.action_detailUserFragment_to_detailUserFragment2, mBundle)

            findNavController().findDestination(R.id.detailUserFragment2) ->
                findNavController().navigate(R.id.action_detailUserFragment2_self,mBundle)
        }
    }

    override fun onItemClicked(user: UserEntity) {
        goToDetails(user)
    }

    override fun onGithubClicked(github_url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(github_url)
        requireActivity().startActivity(intent)
    }

    override fun onFavoriteClicked(user: UserEntity) {
        Log.d(TAG, "Favorite Disabled From The Adapter")
    }

    companion object {
        private const val TAG : String = "FollowersFragment"
    }



}
