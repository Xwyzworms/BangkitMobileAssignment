package com.example.submission2_ezpz.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2_ezpz.R
import com.example.submission2_ezpz.adapters.UserAdapter
import com.example.submission2_ezpz.databinding.FragmentHomeBinding
import com.example.submission2_ezpz.source_data.Result
import com.example.submission2_ezpz.source_data.local.entity.UserEntity
import com.example.submission2_ezpz.utils.ViewModelFactory
import com.example.submission2_ezpz.viewmodels.DetailUserViewModel
import com.example.submission2_ezpz.viewmodels.HomeViewModel
import com.example.submission2_ezpz.viewmodels.UserFavoriteViewModel


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
class HomeFragment : Fragment(), UserAdapter.ItemClickCallback {

    private var _binding :FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var userAdapter : UserAdapter?= null
    private lateinit var factory : ViewModelFactory
    private lateinit var homeFragmentViewModel : HomeViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireActivity(), context?.dataStore as DataStore<Preferences>)
        homeFragmentViewModel = ViewModelProvider(this,factory)[HomeViewModel::class.java]

        val linearLayout = LinearLayoutManager(requireActivity())
        binding.rvHUsers.layoutManager = linearLayout

        homeFragmentViewModel.getUsersData().observe(viewLifecycleOwner) {result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBarH.visibility = View.VISIBLE
                    }
                    is Result.Error -> {
                        binding.progressBarH.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "Terjadi Kesalahan + ${result.error}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    is Result.Success -> {
                        binding.progressBarH.visibility = View.INVISIBLE
                        userAdapter = UserAdapter(result.data)
                        userAdapter?.setOnItemListener(this)
                        binding.rvHUsers.setHasFixedSize(true)
                        binding.rvHUsers.adapter = userAdapter
                    }
                }

            }
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


    private fun goToDetails(user: UserEntity) {
        val mBundle = Bundle()
        mBundle.putParcelable(DetailUserViewModel.EXTRA_USER, user)
        view?.findNavController()?.navigate(R.id.action_homeFragment_to_detailUserFragment2, mBundle)
    }

    private fun setFavorite(user: UserEntity) {

        if(user.isFavorite) homeFragmentViewModel.setFavorite(user,false)
        else homeFragmentViewModel.setFavorite(user,true)
    }

    companion object {
        private const val TAG : String = "HomeFragment"
    }

    override fun onDetailClick(user: UserEntity) {
        goToDetails(user)
    }

    override fun onFavoriteClick(user: UserEntity) {
        setFavorite(user)
        Toast.makeText(requireContext(),"Favorite Added",Toast.LENGTH_LONG).show()
    }

    override fun onGithubIconClick(uri : String ) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(uri)
        requireActivity().startActivity(intent)
    }

}