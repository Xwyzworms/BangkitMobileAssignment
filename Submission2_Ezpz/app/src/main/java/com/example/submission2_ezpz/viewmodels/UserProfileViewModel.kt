package com.example.submission2_ezpz.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.data.UserOwner
import com.example.submission2_ezpz.databinding.FragmentUserProfileBinding
import com.example.submission2_ezpz.networks.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileViewModel : ViewModel() {
    private var _isLoading : MutableLiveData<Boolean> = MutableLiveData()
    private var _userData : MutableLiveData<UserOwner> = MutableLiveData()

    val isLoading : LiveData<Boolean> = _isLoading
    val userData : LiveData<UserOwner> = _userData

    fun getUserData(username : String) {
        _isLoading.value = true
        val client = ApiConfig.ApiService().getDetailUser(username)
        client.enqueue(object : Callback<UserOwner> {
            override fun onResponse(call: Call<UserOwner>, response: Response<UserOwner>) {
                _isLoading.value = false
                if(response.isSuccessful && response.body() != null) {
                    _userData.value = response.body()
                }
            }

            override fun onFailure(call: Call<UserOwner>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "on Failure : ${t.message}")
            }
        })
    }
    companion object {
        private const val TAG = "FollowingViewModel"
    }


}