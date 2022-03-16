package com.example.submission2_ezpz.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.source_data.local.networks.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel(){
    private var _userFollowers = MutableLiveData<List<User>>()
    private var _isLoading = MutableLiveData<Boolean>()

    val isLoading : LiveData<Boolean> = _isLoading
    val userGeneral : LiveData<List<User>> = _userFollowers
    fun getFollowers(username : String?) {
        _isLoading.value = true
        if (username != null && username.isNotEmpty()) {
            val client = ApiConfig.ApiService().getFollowers(username)
            client.enqueue(object : Callback<List<User>> {
                override fun onResponse(
                    call: Call<List<User>>,
                    response: Response<List<User>>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful && response.body() != null) {
                        val responseContent = response.body()
                        _userFollowers.value = responseContent as List<User>
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
        private const val TAG = "FollowersViewModel"
    }
}