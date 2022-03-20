package com.example.submission2_ezpz.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission2_ezpz.source_data.local.entity.UserEntity
import com.example.submission2_ezpz.source_data.local.setting_preference.SettingPreferences
import com.example.submission2_ezpz.source_data.repository.UserRepository
import com.example.submission2_ezpz.source_data.Result
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository : UserRepository,
    private val themePreference : SettingPreferences
) : ViewModel(){

    init {
        getUsersData()
    }

    fun setFavorite(user : UserEntity, favoriteState : Boolean ) {
        viewModelScope.launch {
            userRepository.setFavorites(user,favoriteState)
        }
    }

    fun getUsersData() : LiveData<Result<List<UserEntity>>> {
        return userRepository.getLocalData()
    }

    companion object {
        private const val TAG ="HomeViewModel"
    }


}