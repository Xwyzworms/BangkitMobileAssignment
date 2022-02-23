package com.example.latihanaplikasi1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var edtWidth : EditText
    private lateinit var edtHeight : EditText
    private lateinit var edtLength : EditText
    private lateinit var btnCalculate : Button
    private lateinit var tvResult : TextView

    companion object {
        private const val STATE_RESULT : String = "state_result"
    }
    private fun init () : Unit {

        edtHeight = findViewById(R.id.edt_height)
        edtLength = findViewById(R.id.edt_length)
        edtWidth = findViewById(R.id.edt_width)
        btnCalculate = findViewById(R.id.btn_calculate)
        tvResult = findViewById(R.id.tv_result)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        btnCalculate.setOnClickListener(this)
        if (savedInstanceState != null) {
            tvResult.text = savedInstanceState.getString(STATE_RESULT)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_RESULT, tvResult.text.toString())
    }

    override fun onClick(v0: View) {
        if (v0.id == R.id.btn_calculate) {
            var isSafeToCalculate : Boolean = true
            val inputLength : String = edtLength.text.toString().trim()
            val inputWidth : String = edtWidth.text.toString().trim()
            val inputHeight : String = edtHeight.text.toString().trim()

            if (inputHeight.isEmpty())  {
                edtHeight.error = "Field Height Tidak Boleh Kosong"
                isSafeToCalculate = false
            }
            if (inputLength.isEmpty()) {
                edtLength.error="Field Length tidak boleh kosong"
                isSafeToCalculate = false
            }
            if (inputWidth.isEmpty()) {
                edtWidth.error = "Field Width Tidak boleh kosong"
                isSafeToCalculate = false
            }

            if(isSafeToCalculate) {
                val volume: Double =
                    inputHeight.toDouble() * inputLength.toDouble() * inputWidth.toDouble()
                tvResult.text= volume.toString()
            }
        }
    }
}