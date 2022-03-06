package com.example.sec2_latihanaplikasi10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sec2_latihanaplikasi10.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var mLiveDataTimerViewModel : MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mLiveDataTimerViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        subscribe()


    }

    private fun subscribe() {
        val elapsedTimeObserver = Observer<Long?> {valueLong ->
            val newText = this@MainActivity.resources.getString(R.string.seconds, valueLong)
            binding.timerTextview.text = newText
        }
        mLiveDataTimerViewModel.getElpasedTime().observe(this@MainActivity,elapsedTimeObserver)
    }
}