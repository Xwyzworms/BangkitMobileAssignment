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
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2_ezpz.R
import com.example.submission2_ezpz.adapters.DetailUserAdapter
import com.example.submission2_ezpz.adapters.DetailUserAdapter.OnItemDuClicked
import com.example.submission2_ezpz.databinding.FragmentExploreBinding
import com.example.submission2_ezpz.source_data.local.entity.UserEntity
import com.example.submission2_ezpz.utils.ViewModelFactory
import com.example.submission2_ezpz.viewmodels.DetailUserViewModel
import com.example.submission2_ezpz.viewmodels.ExploreViewModel
import com.example.submission2_ezpz.source_data.Result
import com.example.submission2_ezpz.utils.GeneralUtils

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("settings")


class ExploreFragment : Fragment() {

    private var _binding: FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelExploreFragment: ExploreViewModel
    private lateinit var factory: ViewModelFactory
    private lateinit var detailUserAdapter: DetailUserAdapter
    private lateinit var observer : LiveData<Result<List<UserEntity>>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupOnclickListener(): OnItemDuClicked {
        val listener = object : OnItemDuClicked {
            override fun onItemClicked(user: UserEntity) {
                goToDetails(user)
            }

            override fun onGithubClicked(github_url: String) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(github_url)
                requireActivity().startActivity(intent)
            }

            override fun onFavoriteClicked(user: UserEntity) {
                if (user.isFavorite) viewModelExploreFragment.setFavorite(user, false)
                else viewModelExploreFragment.setFavorite(user, true)
                GeneralUtils.printMessage(requireContext(), user.isFavorite.toString())

            }
        }
        return listener
    }
    private  fun searchUsers(query: String) {

        observer.removeObservers(viewLifecycleOwner)
        observer = viewModelExploreFragment.getData(query)
        observer.observe(viewLifecycleOwner){
            if (it != null) {
                when(it) {
                    is Result.Loading -> {
                        binding.pbFe.visibility = View.VISIBLE
                        binding.ivFeContent.visibility = View.INVISIBLE
                        binding.tvSearchDevTitle.visibility = View.INVISIBLE
                    }
                    is Result.Success -> {

                        if (it.data.isEmpty()) {

                            binding.ivNotFound.visibility = View.VISIBLE
                            binding.pbFe.visibility = View.INVISIBLE
                            binding.rvResult.visibility = View.INVISIBLE
                            binding.rvResult.adapter = null

                        }
                        else {
                            binding.ivNotFound.visibility = View.INVISIBLE
                            binding.pbFe.visibility = View.INVISIBLE
                            binding.ivFeContent.visibility = View.INVISIBLE
                            detailUserAdapter = DetailUserAdapter(it.data)
                            detailUserAdapter.setOnItemDuClickedListener(setupOnclickListener())
                            binding.rvResult.adapter = detailUserAdapter
                        }

                    }
                    is Result.Error -> {
                        binding.pbFe.visibility = View.GONE
                        GeneralUtils.printMessage(requireContext(), it.error)
                    }
                }
            }
                binding.rvResult.visibility = View.VISIBLE
        }
    }


    private fun prepareViewModel() {
        factory = ViewModelFactory.getInstance(requireActivity(), context?.dataStore as DataStore<Preferences>)
        viewModelExploreFragment = ViewModelProvider(this, factory)[ExploreViewModel::class.java]
    }


    private fun prepareTheSearch() {
        binding.apply {
            svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if (query.isNotBlank() && query.isNotEmpty()) {
                        searchUsers(query)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
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

            findNavController().findDestination(R.id.exploreFragment) ->
                findNavController().navigate(R.id.action_exploreFragment_to_detailUserFragment2,mBundle)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewModel()
        observer =  viewModelExploreFragment.getData("")
        prepareTheSearch()
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvResult.setHasFixedSize(true)
        binding.rvResult.layoutManager = layoutManager
    }

    companion object {
        val TAG = ExploreFragment::class.java.simpleName
    }
}

