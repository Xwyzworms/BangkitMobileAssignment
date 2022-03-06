package com.example.sec2_latihanaplikasi10

import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class MainViewModel : ViewModel() {


    private val mInitialTime = SystemClock.elapsedRealtime()
    private val mElapsedTime = MutableLiveData<Long?>()

    init {
        val time = Timer()
        time.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val newValue : Long = (SystemClock.elapsedRealtime() - mInitialTime) / 1000
                mElapsedTime.postValue(newValue)
            }
        }, ONE_SECOND.toLong(), ONE_SECOND.toLong())
    }


    fun getElpasedTime() : LiveData<Long?> {
        return mElapsedTime
    }
    companion object {
        const val ONE_SECOND = 1000
    }


}