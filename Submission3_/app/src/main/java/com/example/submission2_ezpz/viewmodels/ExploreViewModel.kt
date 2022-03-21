package com.example.submission2_ezpz.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submission2_ezpz.source_data.Result
import com.example.submission2_ezpz.source_data.local.entity.UserEntity
import com.example.submission2_ezpz.source_data.local.setting_preference.SettingPreferences
import com.example.submission2_ezpz.source_data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ExploreViewModel(
    private val userRepository: UserRepository,
    private val themePreferences: SettingPreferences
) : ViewModel(){

    fun getData(keyword : String)  : LiveData<Result<List<UserEntity>>> {
            return userRepository.getLocalSearchCoroutines(keyword)

    }

    fun setFavorite(user : UserEntity, favoriteState : Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                userRepository.setFavorites(user, favoriteState)
            }
        }
    }
    fun deleteUser(user:UserEntity) {
        viewModelScope.launch {
            userRepository.removeUser(user)
        }
    }

    companion object {
        private const val TAG = "ExploreViewModel"
    }
}