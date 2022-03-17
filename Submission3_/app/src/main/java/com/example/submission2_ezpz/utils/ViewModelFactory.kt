package com.example.submission2_ezpz.utils

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.submission2_ezpz.source_data.UserRepository
import com.example.submission2_ezpz.source_data.local.setting_preference.SettingPreferences

class ViewModelFactory(private val mRepository: UserRepository, private val mUserSettings : SettingPreferences) : NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance : ViewModelFactory ? = null

        @JvmStatic
        fun getInstance(context : Context) {
            val INSTANCE : ViewModelFactory? = null
            return INSTANCE ?: synchronized(this) {
                INSTANCE = ViewModelFactory(injection)
            }
        }

    }
}