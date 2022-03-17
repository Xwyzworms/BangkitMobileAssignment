package com.example.submission2_ezpz.source_data

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.example.submission2_ezpz.source_data.local.entity.UserEntity
import com.example.submission2_ezpz.source_data.local.networks.ApiInterface
import com.example.submission2_ezpz.source_data.local.room.UserDao
import com.example.submission2_ezpz.source_data.local.setting_preference.SettingPreferences

class UserRepository private constructor (
    private val userDao: UserDao,
    private val userPreferences: SettingPreferences) {


    fun getCurrentTheme() : LiveData<Boolean>{
        return userPreferences.getThemeSettings().asLiveData()
    }

    fun getFavorites() : LiveData<Result<List<UserEntity>>> {
        return userDao.getFavorites().map {
            Result.Success(it)
        }
    }

    fun setFavorites(user : UserEntity, favoriteState : Boolean) {
        user.isFavorite = favoriteState
        userDao.updateFavorites(user)
    }

    companion object {
        private val TAG : String = UserRepository::class.simpleName as String
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