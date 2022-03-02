package com.example.sec2_latihanaplikasi1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.sec2_latihanaplikasi1.databinding.FragmentSafeArgsBinding


class SafeArgsFragment : Fragment() {


    private var _binding : FragmentSafeArgsBinding?= null
    private val binding : FragmentSafeArgsBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nameCategory.text = SafeArgsFragmentArgs.fromBundle(arguments as Bundle).name
        binding.stockCategory.text = SafeArgsFragmentArgs.fromBundle(arguments as Bundle).stock.toString()
        binding.btnProfile.setOnClickListener {
            it.findNavController().navigate(R.id.action_safeArgsFragment_to_homeFragment)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSafeArgsBinding.inflate(inflater,container,false)
        return binding.root
    }


}