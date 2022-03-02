package com.example.sec2_latihanaplikasi1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sec2_latihanaplikasi1.databinding.FragmentCategoryBinding
import com.example.sec2_latihanaplikasi1.databinding.FragmentDetailCategoryBinding


class DetailCategoryFragment : Fragment() {

    private var _binding : FragmentDetailCategoryBinding? = null
    private val binding : FragmentDetailCategoryBinding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dataName : String? = arguments?.getString(CategoryFragment.EXTRA_NAME)
        val dataDescription : Long? = arguments?.getLong(CategoryFragment.EXTRA_STOCK)

        binding.tvCategoryName.text = dataName
        binding.tvCategoryDescription.text = "Stock : ${dataDescription}"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailCategoryBinding.inflate(inflater,container,false)
        return binding.root
    }




}