package com.example.submission2_ezpz.viewmodels

import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2_ezpz.R
import com.example.submission2_ezpz.data.UserOwner
import com.example.submission2_ezpz.networks.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {

    private var _user = MutableLiveData<UserOwner>()
    private var _isLoading = MutableLiveData<Boolean>()

    val user : LiveData<UserOwner> = _user
    val isLoading : LiveData<Boolean> = _isLoading

     fun getUserInformation(username : String) {
        _isLoading.value = true
        val client = ApiConfig.ApiService().getDetailUser(username)
        client.enqueue(object : Callback<UserOwner> {
            override fun onResponse(call: Call<UserOwner>, response: Response<UserOwner>) {
                _isLoading.value = false
                if(response.isSuccessful && response.body() != null) {
                    val user = response.body()
                    _user.value = user as UserOwner
                }
            }
            override fun onFailure(call: Call<UserOwner>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "On Failure : ${t.message}" )
            }
        })
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