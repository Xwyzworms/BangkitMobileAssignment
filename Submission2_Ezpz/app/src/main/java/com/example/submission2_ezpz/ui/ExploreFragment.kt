package com.example.submission2_ezpz.ui


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2_ezpz.R
import com.example.submission2_ezpz.adapters.DetailUserAdapter
import com.example.submission2_ezpz.adapters.DetailUserAdapter.OnItemDuClicked
import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.databinding.FragmentExploreBinding
import com.example.submission2_ezpz.viewmodels.DetailUserViewModel
import com.example.submission2_ezpz.viewmodels.ExploreViewModel

class ExploreFragment : Fragment() {

    private var _binding : FragmentExploreBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelExploreFragment : ExploreViewModel
    private lateinit var detailUserAdapter: DetailUserAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun setupOnclickListener() : OnItemDuClicked {
        val listener = object : OnItemDuClicked {
            override fun onItemClicked(user: User) {
                goToDetails(user)
            }
            override fun onGithubClicked(github_url: String) {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(github_url)
                requireActivity().startActivity(intent)
            }
        }
        return listener
    }
    private fun prepareRv(list_user : List<User>) {

        if(list_user.isNotEmpty()) {
            detailUserAdapter = DetailUserAdapter(list_user)
            val layoutManager = LinearLayoutManager(requireActivity())
            binding.rvResult.layoutManager = layoutManager
            binding.rvResult.setHasFixedSize(true)
            detailUserAdapter.setOnItemDuClickedListener(setupOnclickListener())
            binding.rvResult.adapter = detailUserAdapter
        }
    }
    private fun searchUsers(query : String ) {
        if(query.isEmpty()) return

        viewModelExploreFragment.searchData(query)
        viewModelExploreFragment.resultData.observe(viewLifecycleOwner){
            if (it.isNotEmpty()) {
                prepareRv(it)
                binding.ivNotFound.visibility = View.INVISIBLE
                binding.ivFeContent.visibility = View.INVISIBLE
                binding.tvSearchDevTitle.visibility = View.INVISIBLE
                binding.tvSearchTitle.visibility = View.VISIBLE
            }
            else {
                binding.tvSearchTitle.visibility = View.VISIBLE
                binding.rvResult.visibility = View.INVISIBLE
                binding.ivNotFound.visibility = View.VISIBLE
            }
        }
        viewModelExploreFragment.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }
    private fun showLoading(state : Boolean) {
        if (state)
        {
            binding.pbFe.visibility = View.VISIBLE
            binding.tvFetching.visibility = View.VISIBLE
        }
        else {
            binding.pbFe.visibility = View.INVISIBLE
            binding.tvFetching.visibility = View.INVISIBLE
        }

    }
    private fun prepareViewModel() {
        viewModelExploreFragment = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ExploreViewModel::class.java]
    }
    private fun prepareTheSearch() {
        binding.apply {
            svUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        searchUsers(query)
                    }

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
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

            findNavController().findDestination(R.id.exploreFragment) ->
                findNavController().navigate(R.id.action_exploreFragment_to_detailUserFragment2,mBundle)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewModel()
        prepareTheSearch()
    }




}