package com.example.sec2_latihanaplikasi3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sec2_latihanaplikasi3.databinding.ActivitySubwayBinding

class SubwayActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySubwayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubwayBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}