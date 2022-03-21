package com.example.sec3_latihanaplikasi20_service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log

class MyBoundService : Service() {

    private val mBinder = MyBinder()
    private val startTime = System.currentTimeMillis()

    private val mTimer : CountDownTimer = object : CountDownTimer(100000,1000 ) {
        override fun onTick(p0: Long) {
            val elapsedTime = System.currentTimeMillis() - startTime
            Log.d(TAG, "onTick : $elapsedTime")
        }

        override fun onFinish() {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "OnDestroy")
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d(TAG, "OnBind")
        mTimer.start()
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "OnCreate")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "OnUnbind")
        mTimer.cancel()
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d(TAG, "OnRebind")
    }

    internal inner class MyBinder : Binder() {
        val getService : MyBoundService = this@MyBoundService
    }

    companion object {
        private val TAG = MyBoundService::class.java.simpleName
    }

}