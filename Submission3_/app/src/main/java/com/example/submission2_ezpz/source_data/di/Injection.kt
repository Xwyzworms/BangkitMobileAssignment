package com.example.submission2_ezpz.source_data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.submission2_ezpz.source_data.repository.UserRepository
import com.example.submission2_ezpz.source_data.local.networks.ApiConfig
import com.example.submission2_ezpz.source_data.local.networks.ApiInterface
import com.example.submission2_ezpz.source_data.local.room.UserDatabase
import com.example.submission2_ezpz.source_data.local.setting_preference.SettingPreferences

object Injection {
    fun ProvideRepository(context : Context,  dataStore : DataStore<Preferences>) : UserRepository {
        val apiService : ApiInterface = ApiConfig.ApiService()
        val database  = UserDatabase.getDatabaseInstance(context)
        val dao = database.userDao()
        return UserRepository.getInstance(apiService,dao ,ProvideUserSettings(dataStore))
    }

    fun ProvideUserSettings(datastore : DataStore<Preferences>) : SettingPreferences{
        return SettingPreferences.getInstance(datastore)
    }
}