package com.example.sec3_latihanaplikasi13_unittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.example.sec3_latihanaplikasi13_unittest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = MainViewModel(CuboidModel())

        binding.btnCalculateCircumference.setOnClickListener(this)
        binding.btnCalculateSurfaceArea.setOnClickListener(this)
        binding.btnCalculateVolume.setOnClickListener(this)
        binding.btnSave.setOnClickListener(this)

    }

    override fun onClick(p0: View) {
        val width: String = binding.edtWidth.text.toString().trim()
        val length: String = binding.edtLength.text.toString().trim()
        val height: String = binding.edtHeight.text.toString().trim()

        when {
            TextUtils.isEmpty(length) -> {
                binding.edtLength.error = "Field ini tidak boleh kosong"
            }
            TextUtils.isEmpty(width) -> {
                binding.edtWidth.error = "Field ini tidak boleh kosong"
            }
            TextUtils.isEmpty(height) -> {
                binding.edtHeight.error = "Field ini tidak boleh kosong"
            }

            else -> {
                val value_length = length.toFloat()
                val value_width = width.toFloat()
                val value_height = height.toFloat()


                when (p0.id) {
                    R.id.btn_calculate_circumference -> {
                        binding.tvResult.text = mainViewModel.getCircumference().toString()
                        goneOrNot(false)
                    }
                    R.id.btn_calculate_surface_area -> {
                        binding.tvResult.text = mainViewModel.getSurfaceArea().toString()
                        goneOrNot(false)
                    }
                    R.id.btn_calculate_volume -> {
                        binding.tvResult.text = mainViewModel.getVolume().toString()
                        goneOrNot(false)
                    }
                    R.id.btn_save -> {
                        mainViewModel.save(value_width, value_length, value_height)
                        goneOrNot(true)
                    }
                }
            }
        }
    }


    private fun goneOrNot(state: Boolean) {
        val gone = View.GONE
        val visible = View.VISIBLE
        if (state) {
            binding.btnSave.visibility = gone
            binding.btnCalculateVolume.visibility = visible
            binding.btnCalculateCircumference.visibility = visible
            binding.btnCalculateSurfaceArea.visibility = visible
        }
        else {
            binding.btnSave.visibility = visible
            binding.btnCalculateVolume.visibility = gone
            binding.btnCalculateCircumference.visibility = gone
            binding.btnCalculateSurfaceArea.visibility = gone
        }

    }
}