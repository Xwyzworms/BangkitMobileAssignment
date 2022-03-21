package com.example.submission2_ezpz.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission2_ezpz.source_data.local.entity.UserEntity
import com.example.submission2_ezpz.source_data.local.setting_preference.SettingPreferences
import com.example.submission2_ezpz.source_data.repository.UserRepository
import com.example.submission2_ezpz.source_data.Result
import kotlinx.coroutines.launch

class FollowersViewModel
    (private val userRepository : UserRepository,
     private val themePreference : SettingPreferences
): ViewModel(){


    fun getFollowers(username : String)  : LiveData<Result<List<UserEntity>>>{
        return userRepository.getFollowers(username)
    }
    fun setFavorite(user : UserEntity , favoriteState : Boolean) {
        viewModelScope.launch {
            userRepository.setFavorites(user,favoriteState)
        }
    }
    companion object {
        private const val TAG = "FollowersViewModel"
    }
}