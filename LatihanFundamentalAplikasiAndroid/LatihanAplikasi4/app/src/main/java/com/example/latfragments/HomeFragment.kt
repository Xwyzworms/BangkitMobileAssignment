package com.example.latfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager


class HomeFragment : Fragment(), View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnCategory : Button = view.findViewById(R.id.btn_category)
        val btnTambahan : Button = view.findViewById(R.id.btn_addons)
        btnTambahan.setOnClickListener(this)
        btnCategory.setOnClickListener(this)

    }

    override fun onClick(v : View ) {
        when (v.id) {

            R.id.btn_category -> {
                val mCategoryFragment = CategoryFragment()
                val mFragmentManager : FragmentManager = parentFragmentManager
                mFragmentManager.beginTransaction().apply {
                replace(R.id.frame_container, mCategoryFragment, CategoryFragment::class.java.simpleName)
                addToBackStack(null)
                }.commit()
            }

            R.id.btn_addons -> {
                val mAddonsFragment :AdditionFragment =AdditionFragment()
                val mFragmentManager = parentFragmentManager
                mFragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, mAddonsFragment, AdditionFragment::class.java.simpleName)
                    addToBackStack(null)
                }.commit()
            }
        }
    }
}