package com.example.latfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


class AdditionFragment : Fragment(), View.OnClickListener {
    private lateinit var tv_addition_ganteng : TextView

    companion object {
        const val TAG : String = "AdditionFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addition, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_addition_ganteng = view.findViewById(R.id.tv_addition_ganteng)

        val btn_show_answer : Button = view.findViewById(R.id.btn_addition_show_data)

        btn_show_answer.setOnClickListener(this )

    }

    override fun onClick(p0: View) {
        if(p0.id == R.id.btn_addition_show_data) {
            tv_addition_ganteng.text = "Ganteng Banget !"
        }
    }


}