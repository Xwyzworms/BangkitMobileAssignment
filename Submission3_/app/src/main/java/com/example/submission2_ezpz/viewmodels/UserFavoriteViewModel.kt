package com.example.submission2_ezpz.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.submission2_ezpz.source_data.local.entity.UserEntity
import com.example.submission2_ezpz.source_data.local.setting_preference.SettingPreferences
import com.example.submission2_ezpz.source_data.repository.UserRepository
import com.example.submission2_ezpz.source_data.Result

class UserFavoriteViewModel(private val userRepository : UserRepository,
                            private val themePreference : SettingPreferences ): ViewModel() {

    private var loading_ = MutableLiveData<Boolean>()
    private val status_message_ = MutableLiveData<String>()
    private val theme_status_ = MutableLiveData<Boolean>()

    val theme_status : LiveData<Boolean> = theme_status_
    val status_message : LiveData<String> = status_message_
    val loading : LiveData<Boolean> get() = loading_

    fun insertFavorite(user : UserEntity) {
        userRepository.setFavorites(user, true)
        status_message_.value = "User Inserted ..."
    }

    fun getFavorites() : LiveData<Result<List<UserEntity>>> {
        return userRepository.getFavorites()
    }

    fun removeFavorite(user : UserEntity) {
        userRepository.setFavorites(user, false)
        status_message_.value = "User Removed ..."
    }

    fun loadTheme() : LiveData<Boolean>{
        val theme = themePreference.getThemeSettings().asLiveData()
        return theme
    }
    


}