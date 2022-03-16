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

class HomeViewModel : ViewModel(){


    private var _isLoading = MutableLiveData<Boolean>()
    private var _userL = MutableLiveData<List<User>>()

    val users: LiveData<List<User>> = _userL
    val isLoading : LiveData<Boolean> = _isLoading

    init {
        getUsersData()
    }
    private fun getUsersData() {
        _isLoading.value = true
        val client = ApiConfig.ApiService().getUsers()
        client.enqueue(object  : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
                    val responseData = response.body()
                    _userL.value = responseData as List<User>
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG,"On Failure: ${t.message}")
            }
        })
    }



    companion object {
        private const val TAG ="HomeViewModel"
    }


}