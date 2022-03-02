package com.example.sec2_latihanaplikasi1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.sec2_latihanaplikasi1.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {

    private var _binding : FragmentCategoryBinding? = null
    private val binding : FragmentCategoryBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCategoryLifestyle.setOnClickListener { view ->
            val mBundle = Bundle()
            mBundle.putString(EXTRA_NAME, "LifeStyle")
            mBundle.putLong(EXTRA_STOCK, 7)
            view.findNavController().navigate(R.id.action_categoryFragment_to_detailCategoryFragment,mBundle)
        }

        binding.btnTambahan.setOnClickListener {
            val toSafeArgsFragment = CategoryFragmentDirections.actionCategoryFragmentToSafeArgsFragment()
            toSafeArgsFragment.name = "LifeStyle"
            toSafeArgsFragment.stock = 7
            it.findNavController().navigate(toSafeArgsFragment)
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        const val EXTRA_NAME : String = "extra_name"
        const val EXTRA_STOCK : String = "extra_stock"
    }

}