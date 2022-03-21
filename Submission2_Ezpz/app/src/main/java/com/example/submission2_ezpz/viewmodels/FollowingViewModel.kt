package com.example.submission2_ezpz.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.networks.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel(){

    private var _userFollowing = MutableLiveData<List<User>>()
    val userFollowing : LiveData<List<User>> = _userFollowing

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun getFollowing(username: String?) {
        _isLoading.value = true
        if (username != null) {
            val client = ApiConfig.ApiService().getFollowing(username)
            client.enqueue(object : Callback<List<User>> {
                override fun onResponse(
                    call: Call<List<User>>,
                    response: Response<List<User>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        val responseContent = response.body()
                        _userFollowing.value = responseContent as List<User>
                    }
                }
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                    _isLoading.value = false
                    Log.d(TAG, "OnFailure: ${t.message}")
                }
            })
        }
    }

    companion object {
        private const val TAG = "FollowingViewModel"
    }
}