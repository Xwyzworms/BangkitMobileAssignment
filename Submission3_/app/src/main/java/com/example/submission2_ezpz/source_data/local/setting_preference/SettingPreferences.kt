package com.example.submission2_ezpz.source_data.local.setting_preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.submission2_ezpz.data.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SettingPreferences private constructor(private val dataStore : DataStore<Preferences>) {


    private val THEME_KEY : Preferences.Key<Boolean> = booleanPreferencesKey("theme_key")
    private val USER_KEY  : Preferences.Key<String> = stringPreferencesKey("user_username")



    fun getThemeSettings() : Flow<Boolean> {
        return dataStore.data.map { preference ->
            preference[THEME_KEY] ?: false
        }
    }

    fun getUserSettings() : Flow<String> {
        return dataStore.data.map {
            it[USER_KEY] ?: ""
        }
    }

    suspend fun saveThemeSettings(isDarkModeActive : Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    suspend fun saveUserSettings(user_login : String) {
        dataStore.edit { preferences ->
            preferences[USER_KEY] = user_login
        }
    }

    companion object {
        @Volatile
        private var INSTANCE : SettingPreferences? = null

        fun getInstance(dataStore : DataStore<Preferences>) : SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}