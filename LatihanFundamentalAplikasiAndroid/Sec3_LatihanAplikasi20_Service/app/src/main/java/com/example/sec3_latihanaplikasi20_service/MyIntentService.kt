package com.example.sec3_latihanaplikasi20_service

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService


class MyIntentService : JobIntentService() {


    companion object {
        private const val JOB_ID = 1000
        internal const val EXTRA_DURATION = "extra_duration"
        private val TAG = MyIntentService::class.java.simpleName

        fun enqueueWorks(context : Context, intent: Intent) {
            enqueueWork(context, MyIntentService::class.java, JOB_ID, intent)
        }
    }


    override fun onHandleWork(intent: Intent) {
        Log.d(TAG, "OnHandleWork : Mulai....")
        val duration = intent.getLongExtra(EXTRA_DURATION, 0)
        try {
            Thread.sleep(duration)
            Log.d(TAG, "onHandleWork : Selesai")
        } catch (e : InterruptedException) {
            e.printStackTrace()
            Thread.currentThread().interrupt()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }

}