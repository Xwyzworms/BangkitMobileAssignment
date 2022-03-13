package com.example.sec2_latihanaplikasi9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.sec2_latihanaplikasi9.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    private lateinit var binding : ActivityMainBinding
    private lateinit var activityViewModel : MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activityViewModel = ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)
        displayResult()

        binding.btnCalculate.setOnClickListener {
            val width = binding.edtWidth.text.toString()
            val height = binding.edtHeight.text.toString()
            val length = binding.edtLength.text.toString()

            when {
                width.isEmpty() -> {
                    binding.edtWidth.error = "Masih Kosong"
                }
                height.isEmpty() -> {
                    binding.edtHeight.error = "Masih Kosong"
                }
                length.isEmpty() -> {
                    binding.edtLength.error = "Masih kosong"
                }

                else -> {
                    activityViewModel.calculate(width,height,length)
                    displayResult()
                }
            }
        }
    }

    private fun displayResult() {
        binding.tvResult.text = activityViewModel.result.toString()
    }
}