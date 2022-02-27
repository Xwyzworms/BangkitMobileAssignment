package com.example.latfragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


class DetailCategoryFragment : Fragment() {

    lateinit var tvCategoryName : TextView
    lateinit var tvCategoryDescription : TextView
    lateinit var btnProfile : Button
    lateinit var btnShowDialog : Button

    var description : String ?= null

    companion object {
        var EXTRA_NAME : String = "extra_name"
        var EXTRA_DESCRIPTION : String = "extra_description"

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvCategoryDescription = view.findViewById(R.id.tv_category_description)
        tvCategoryName = view.findViewById(R.id.tv_category_name)
        btnProfile = view.findViewById(R.id.btn_profile)
        btnShowDialog = view.findViewById(R.id.btn_show_dialog)

        if(savedInstanceState != null) {
            val descFromBundle = savedInstanceState.getString(EXTRA_NAME)
            description = descFromBundle
        }

        if(arguments != null) {
            val categoryName : String? = arguments?.getString(EXTRA_NAME)
            tvCategoryName.text = categoryName
            tvCategoryDescription.text = description
        }


        btnProfile.setOnClickListener {
            val mIntent : Intent = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(mIntent)
        }

        btnShowDialog.setOnClickListener {
            val mOptionDialogFragment = OptionDialogFragment()

            val mFragmentManager = childFragmentManager
            mOptionDialogFragment.show(mFragmentManager, OptionDialogFragment::class.java.simpleName)

        }
    }
    var optionDialogListener : OptionDialogFragment.onOptionDialogListener = object : OptionDialogFragment.onOptionDialogListener {
        override fun onOptionChosen(text: String?) {
            Toast.makeText(requireActivity(), text, Toast.LENGTH_LONG).show()
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_category, container, false)
    }



}