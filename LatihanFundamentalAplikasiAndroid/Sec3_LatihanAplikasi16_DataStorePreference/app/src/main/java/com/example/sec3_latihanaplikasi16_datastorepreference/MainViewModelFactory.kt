package com.example.sec3_latihanaplikasi16_datastorepreference

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MainViewModelFactory(val pref : SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        throw  IllegalArgumentException("Unknown View Model Class : "+ modelClass.simpleName)
    }
}