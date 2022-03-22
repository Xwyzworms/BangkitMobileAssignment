package com.example.submission2_ezpz.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.submission2_ezpz.data.UserOwner
import com.example.submission2_ezpz.source_data.local.networks.ApiConfig
import com.example.submission2_ezpz.source_data.local.setting_preference.SettingPreferences
import com.example.submission2_ezpz.source_data.repository.UserRepository
import com.example.submission2_ezpz.source_data.Result
import kotlinx.coroutines.launch

class UserProfileViewModel(
    private val userRepository: UserRepository,
    private val themePreferences: SettingPreferences
) : ViewModel() {

    fun getUserData(username : String) : LiveData<Result<UserOwner>> {
        return userRepository.getUserInfoRemote(username)
    }

    fun getThemeSettings() : LiveData<Boolean> {
        return themePreferences.getThemeSettings().asLiveData()
    }
    fun saveThemeSettings(isDarkModeActive : Boolean) {
        viewModelScope.launch {
            themePreferences.saveThemeSettings(isDarkModeActive)
        }
    }

    companion object {
        private const val TAG = "FollowingViewModel"
    }


}