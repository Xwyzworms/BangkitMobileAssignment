package com.example.submission2_ezpz.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2_ezpz.R
import com.example.submission2_ezpz.adapters.DetailUserAdapter
import com.example.submission2_ezpz.adapters.SectionPagerAdapter
import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.databinding.FragmentFollowersBinding
import com.example.submission2_ezpz.viewmodels.DetailUserViewModel
import com.example.submission2_ezpz.viewmodels.FollowersViewModel


class FollowersFragment : Fragment(), DetailUserAdapter.OnItemDuClicked {

    private  var _binding : FragmentFollowersBinding? =null
    private val binding : FragmentFollowersBinding get() = _binding!!
    private lateinit var viewModelFollowersFragment : FollowersViewModel
    private var user : User? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = arguments?.getParcelable(SectionPagerAdapter.ARG_NAME)
        settingUpViewModel()
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

    private fun showLoading(state : Boolean) {
        if (state)
        {
            binding.pbFollowers.visibility = View.VISIBLE
            binding.tvFollowersTextTitle.visibility = View.VISIBLE
        }
        else {
            binding.pbFollowers.visibility = View.INVISIBLE
            binding.tvFollowersTextTitle.visibility = View.INVISIBLE
        }
    }
    private fun settingUpRv(list_Data : List<User>) {
        val linearLayout = LinearLayoutManager(requireActivity())
        val adapter = DetailUserAdapter(list_Data)
        adapter.setOnItemDuClickedListener(this)
        binding.rvFollowers.layoutManager = linearLayout
        binding.rvFollowers.setHasFixedSize(true)
        binding.rvFollowers.adapter = adapter
    }

    private fun settingUpViewModel() {
        viewModelFollowersFragment = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        )[FollowersViewModel::class.java]

        if (user != null) {
            viewModelFollowersFragment.getFollowers(user?.username)
            viewModelFollowersFragment.userGeneral.observe(viewLifecycleOwner) {
                settingUpRv(it)
            }
            viewModelFollowersFragment.isLoading.observe(viewLifecycleOwner){
                showLoading(it)
            }
        }
        else {
            Log.d(TAG, "User is null")
        }
    }

    private fun goToDetails(user : User) {
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

    override fun onItemClicked(user: User) {
        goToDetails(user)
    }

    override fun onGithubClicked(github_url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(github_url)
        requireActivity().startActivity(intent)
    }

    companion object {
        private const val TAG : String = "FollowersFragment"
    }

}