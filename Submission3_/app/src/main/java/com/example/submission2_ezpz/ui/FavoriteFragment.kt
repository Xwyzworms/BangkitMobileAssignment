package com.example.submission2_ezpz.ui

import com.example.submission2_ezpz.source_data.Result
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2_ezpz.R
import com.example.submission2_ezpz.adapters.DetailUserAdapter
import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.databinding.FragmentFavoriteBinding
import com.example.submission2_ezpz.source_data.local.entity.UserEntity
import com.example.submission2_ezpz.utils.GeneralUtils
import com.example.submission2_ezpz.utils.ViewModelFactory
import com.example.submission2_ezpz.viewmodels.DetailUserViewModel
import com.example.submission2_ezpz.viewmodels.UserFavoriteViewModel


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")
class FavoriteFragment : Fragment() {

    private var binding : FragmentFavoriteBinding ?= null
    private val _binding get() = binding!!

    private lateinit var factory : ViewModelFactory
    private lateinit var viewModelFragmentFavorite: UserFavoriteViewModel
    private lateinit var itemListener : DetailUserAdapter.OnItemDuClicked
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        factory = ViewModelFactory.getInstance(requireContext(), context?.dataStore as DataStore<Preferences>)
        viewModelFragmentFavorite = ViewModelProvider(this, factory).get(UserFavoriteViewModel::class.java)
        val linearLayout = LinearLayoutManager(requireContext())
        _binding.rvFavorite.layoutManager = linearLayout
        prepareViewModel()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return _binding.root
    }

    private fun gotoDetails(user : UserEntity) {
        val mBundle = Bundle()
        mBundle.putParcelable(DetailUserViewModel.EXTRA_USER, user)

        findNavController().navigate(R.id.action_favoriteFragment_to_detailUserFragment2, mBundle)
    }

    private fun setupListener() {
        itemListener = object : DetailUserAdapter.OnItemDuClicked {
            override fun onItemClicked(user: UserEntity) {
                gotoDetails(user)
            }

            override fun onGithubClicked(github_url: String) {
                GeneralUtils.onGithubClick(requireActivity(),github_url)
            }

            override fun onFavoriteClicked(user: UserEntity) {
                viewModelFragmentFavorite.removeFavorite(user)
            }

        }
    }
    private fun prepareViewModel() {


        viewModelFragmentFavorite.getFavorites().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when(result) {

                    is Result.Loading -> {
                        _binding.pbFf.visibility = View.VISIBLE
                        _binding.tvFfFetching.visibility = View.VISIBLE
                        _binding.rvFavorite.visibility = View.INVISIBLE

                    }
                    is Result.Success -> {

                        Log.d(TAG, result.data.toString())
                        _binding.pbFf.visibility = View.INVISIBLE
                        _binding.tvFfFetching.visibility = View.INVISIBLE
                        _binding.rvFavorite.visibility = View.VISIBLE
                        _binding.tvFavTitle.visibility  = View.VISIBLE
                        if(result.data.isEmpty()) {
                            _binding.tvNotes.visibility = View.VISIBLE
                        }
                        else {
                            val adapter = DetailUserAdapter(result.data)
                            adapter.setOnItemDuClickedListener(itemListener)
                            _binding.rvFavorite.adapter = adapter
                        }
                    }
                    is Result.Error -> {
                        GeneralUtils.printMessage(requireContext(), result.error)
                    }
                }
            }
        }

    }

    companion object {
        private val TAG : String = FavoriteFragment::class.java.simpleName
    }
}