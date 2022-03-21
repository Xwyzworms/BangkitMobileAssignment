package com.example.submission2_ezpz.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2_ezpz.R
import com.example.submission2_ezpz.adapters.UserAdapter
import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.databinding.FragmentHomeBinding
import com.example.submission2_ezpz.viewmodels.DetailUserViewModel
import com.example.submission2_ezpz.viewmodels.HomeViewModel


class HomeFragment : Fragment(), UserAdapter.ItemClickCallback {

    private var _binding :FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var userAdapter : UserAdapter?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val homeFragmentViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory())[HomeViewModel::class.java]
        val linearLayout = LinearLayoutManager(requireActivity())
        binding.rvHUsers.layoutManager = linearLayout
        homeFragmentViewModel.users.observe(viewLifecycleOwner) {list_user ->
            userAdapter = UserAdapter(list_user)
            userAdapter?.setOnItemListener(this)
            binding.rvHUsers.adapter = userAdapter
        }

        homeFragmentViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(state : Boolean) {
        if(state) {
            binding.progressBarH.visibility = View.VISIBLE
        }
        else {
            binding.progressBarH.visibility = View.INVISIBLE
        }
    }

    private fun goToDetails(user : User) {
        val mBundle = Bundle()
        mBundle.putParcelable(DetailUserViewModel.EXTRA_USER, user)
        view?.findNavController()?.navigate(R.id.action_homeFragment_to_detailUserFragment2, mBundle)
    }

    companion object {
        private const val TAG : String = "HomeFragment"
    }

    override fun onDetailClick(user: User) {
        goToDetails(user)
    }

    override fun onGithubIconClick(uri : String ) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(uri)
        requireActivity().startActivity(intent)
    }
}