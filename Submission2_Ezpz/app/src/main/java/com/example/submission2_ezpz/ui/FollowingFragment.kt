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
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2_ezpz.R
import com.example.submission2_ezpz.adapters.DetailUserAdapter
import com.example.submission2_ezpz.adapters.SectionPagerAdapter
import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.databinding.FragmentFollowingBinding
import com.example.submission2_ezpz.viewmodels.DetailUserViewModel
import com.example.submission2_ezpz.viewmodels.FollowingViewModel


class FollowingFragment : Fragment() {

    private var _binding : FragmentFollowingBinding? = null
    private val binding : FragmentFollowingBinding get() = _binding!!

    private lateinit var itemListener :DetailUserAdapter.OnItemDuClicked
    private var user : User? = null

    private lateinit var viewModelFollowingFragment : FollowingViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = arguments?.getParcelable(SectionPagerAdapter.ARG_NAME)
        settingUpViewModel()

         itemListener = object : DetailUserAdapter.OnItemDuClicked{
            override fun onItemClicked(user: User) {
                goToDetails(user)
            }

            override fun onGithubClicked(github_url: String) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(github_url)
                requireActivity().startActivity(intent)
            }
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
    private fun setUpTheRv(list_user : List<User>) {
        val linearLayout = LinearLayoutManager(requireActivity())
        val adapter = DetailUserAdapter(list_user)
        adapter.setOnItemDuClickedListener(itemListener)
        binding.rvFollowing.layoutManager = linearLayout
        binding.rvFollowing.setHasFixedSize(true)
        binding.rvFollowing.adapter = adapter
    }

    private fun showLoading(state : Boolean) {
        if (state)
        {
            binding.pbFollowing.visibility = View.VISIBLE
            binding.tvFollowingTextTitle.visibility = View.VISIBLE
        }
        else {
            binding.pbFollowing.visibility = View.INVISIBLE
            binding.tvFollowingTextTitle.visibility = View.INVISIBLE
        }

    }

    private fun goToDetails(user : User) {
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
        viewModelFollowingFragment = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowingViewModel::class.java]
        if(user != null) {
            viewModelFollowingFragment.getFollowing(user?.username)
            viewModelFollowingFragment.userFollowing.observe(viewLifecycleOwner) {
                setUpTheRv(it)
            }
            viewModelFollowingFragment.isLoading.observe(viewLifecycleOwner){
                showLoading(it)
            }
        }
    }

}