package com.example.sec3_latihanaplikasi20_service

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.sec3_latihanaplikasi20_service.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var mServiceBound : Boolean = false
    private lateinit var  mBoundService : MyBoundService
    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val myBinder = service as MyBoundService.MyBinder
            mBoundService = myBinder.getService
            mServiceBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mServiceBound = false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStartJobIntentService.setOnClickListener {
            val mStartIntentService = Intent(this, MyIntentService::class.java)
            mStartIntentService.putExtra(MyIntentService.EXTRA_DURATION, 5000L)
            MyIntentService.enqueueWorks(this, mStartIntentService)
        }

        binding.btnStartServices.setOnClickListener {
            val mStartServiceIntent = Intent(this, MyService::class.java)
            startService(mStartServiceIntent)
        }

        binding.btnStartBoundService.setOnClickListener {
            val mBoundServiceIntent = Intent(this, MyBoundService::class.java)
            bindService(mBoundServiceIntent, mServiceConnection, BIND_AUTO_CREATE)
        }

        binding.btnStopBoundService.setOnClickListener {
            unbindService(mServiceConnection)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (mServiceBound) {
            unbindService(mServiceConnection)
        }
    }
}