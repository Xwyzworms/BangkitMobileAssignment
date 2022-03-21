package com.example.sec3_latihanaplikasi18_room.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sec3_latihanaplikasi18_room.ui.insert.NoteAddUpdateViewModel
import com.example.sec3_latihanaplikasi18_room.ui.main.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelfactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory(){

    companion object {
        @Volatile
        private var instance : ViewModelfactory? = null

        @JvmStatic
        fun getInstance(application : Application) : ViewModelfactory {
            if (instance == null) {
                synchronized(ViewModelfactory::class.java) {
                    instance = ViewModelfactory(application)
                }
            }
            return instance as ViewModelfactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mApplication) as T
        }
        else if (modelClass.isAssignableFrom(NoteAddUpdateViewModel::class.java)) {
            return NoteAddUpdateViewModel(mApplication) as T
        }

        throw IllegalArgumentException("Unknown View Model ${modelClass::class.java.simpleName}")
    }
}