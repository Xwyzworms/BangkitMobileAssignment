package com.example.submission2_ezpz.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.bumptech.glide.load.model.ModelCache
import com.example.submission2_ezpz.source_data.repository.UserRepository
import com.example.submission2_ezpz.source_data.di.Injection
import com.example.submission2_ezpz.source_data.local.networks.ApiInterface
import com.example.submission2_ezpz.source_data.local.setting_preference.SettingPreferences
import com.example.submission2_ezpz.viewmodels.*

class ViewModelFactory(private val mRepository: UserRepository, private val mUserSettings : SettingPreferences) : NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            @Suppress("UNCHECKED_CAST")
            modelClass.isAssignableFrom(UserFavoriteViewModel::class.java) -> {
                return UserFavoriteViewModel(mRepository, mUserSettings) as T
            }
            @Suppress("UNCHECKED_CAST")
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                return HomeViewModel(mRepository, mUserSettings) as T
            }
            @Suppress("UNCHECKED_CAST")
            modelClass.isAssignableFrom(ExploreViewModel::class.java) -> {
                return ExploreViewModel(mRepository,mUserSettings) as T
            }
            @Suppress("UNCHECKED_CAST")
            modelClass.isAssignableFrom(FollowersViewModel::class.java) -> {
                return FollowersViewModel(mRepository, mUserSettings) as T
            }
            @Suppress("UNCHECKED_CAST")
            modelClass.isAssignableFrom(FollowingViewModel::class.java) -> {
                return FollowingViewModel(mRepository, mUserSettings) as T
            }
            @Suppress("UNCHECKED_CAST")
            modelClass.isAssignableFrom(DetailUserViewModel::class.java) -> {
                return DetailUserViewModel(mRepository, mUserSettings) as T
            }
            @Suppress("UNCHECKED_CAST")
            modelClass.isAssignableFrom(UserProfileViewModel::class.java) -> {
                return UserProfileViewModel(mRepository, mUserSettings) as T
            }
        }
        throw IllegalArgumentException("Incorrect ViewModel Class ${modelClass.simpleName}")

    }

    companion object {
        @Volatile
        private var INSTANCE : ViewModelFactory ? = null

        @JvmStatic
        fun getInstance(context : Context, mUserSettings : DataStore<Preferences>) : ViewModelFactory{
            return INSTANCE ?: synchronized(this) {
                val instance = ViewModelFactory(Injection.ProvideRepository(context),Injection.ProvideUserSettings(mUserSettings))
                INSTANCE = instance
                instance
            }
        }

    }
}