package com.example.submission2_ezpz.viewmodels

import androidx.lifecycle.*
import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.source_data.local.entity.UserEntity
import com.example.submission2_ezpz.source_data.local.setting_preference.SettingPreferences
import com.example.submission2_ezpz.source_data.repository.UserRepository
import com.example.submission2_ezpz.source_data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class UserFavoriteViewModel(private val userRepository : UserRepository,
                            private val themePreference : SettingPreferences ): ViewModel() {

    private var loading_ = MutableLiveData<Boolean>()
    private val status_message_ = MutableLiveData<String>()
    private val theme_status_ = MutableLiveData<Boolean>()

    val theme_status : LiveData<Boolean> = theme_status_
    val status_message : LiveData<String> = status_message_
    val loading : LiveData<Boolean> get() = loading_

    fun insertFavorite(user : UserEntity) {

        viewModelScope.launch {
        userRepository.setFavorites(user, true)
        status_message_.value = "User Inserted ..."
        }
    }

    fun getFavorites() : LiveData<Result<List<UserEntity>>> {
        return userRepository.getFavorites()
    }


    fun removeFavorite(user : UserEntity) {
        viewModelScope.launch {
            userRepository.removeUser(user)
        }
    }

    fun loadTheme() : LiveData<Boolean>{
        val theme = themePreference.getThemeSettings().asLiveData()
        return theme
    }



}