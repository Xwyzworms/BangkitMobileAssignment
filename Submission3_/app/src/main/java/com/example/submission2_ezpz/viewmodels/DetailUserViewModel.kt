package com.example.submission2_ezpz.viewmodels

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.submission2_ezpz.R
import com.example.submission2_ezpz.data.UserOwner
import com.example.submission2_ezpz.source_data.local.entity.UserEntity
import com.example.submission2_ezpz.source_data.local.setting_preference.SettingPreferences
import com.example.submission2_ezpz.source_data.repository.UserRepository
import com.example.submission2_ezpz.source_data.Result
import kotlinx.coroutines.launch

class DetailUserViewModel
    ( private val mRepository: UserRepository,
      private val mUserSetting: SettingPreferences)
    : ViewModel() {

    fun getUserInformation(username : String ) : LiveData<Result<UserOwner>>  {
        return mRepository.getUserInfoRemote(username)
    }


    fun setUserFavorite(user : UserEntity, state : Boolean) {
        viewModelScope.launch {
            mRepository.setFavorites(user,state)
        }
    }

    companion object {
        const val TAG : String = "DetailUserViewModel"
        const val EXTRA_USER : String = "user"
        @StringRes
        val TAB_TITLES : IntArray = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}