package com.example.submission2_ezpz.source_data

import com.example.submission2_ezpz.source_data.local.networks.ApiInterface
import com.example.submission2_ezpz.source_data.local.room.UserDao
import com.example.submission2_ezpz.source_data.local.setting_preference.SettingPreferences

class UserRepository private constructor (
    private val apiService: ApiInterface,
    private val userDao: UserDao,
    private val userPreferences: SettingPreferences) {





    companion object {
        @Volatile
        private var INSTANCE : UserRepository ? = null

        fun getInstance(
            apiService : ApiInterface
            ,userDao : UserDao
            ,userPreferences : SettingPreferences ) : UserRepository
        {
            return INSTANCE ?: synchronized(this){
                val instance = UserRepository(apiService,userDao,userPreferences)
                INSTANCE = instance
                instance
            }
        }
    }
}