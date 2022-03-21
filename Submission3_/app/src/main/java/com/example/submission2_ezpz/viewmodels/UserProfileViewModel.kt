package com.example.submission2_ezpz.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2_ezpz.data.UserOwner
import com.example.submission2_ezpz.source_data.local.networks.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileViewModel : ViewModel() {
    private var _isLoading : MutableLiveData<Boolean> = MutableLiveData()
    private var _userData : MutableLiveData<UserOwner> = MutableLiveData()

    val isLoading : LiveData<Boolean> = _isLoading
    val userData : LiveData<UserOwner> = _userData

    fun getUserData(username : String) {    }
    companion object {
        private const val TAG = "FollowingViewModel"
    }


}