package com.example.submission2_ezpz.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2_ezpz.data.SearchResult
import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.networks.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ExploreViewModel : ViewModel(){

    private var _isLoading = MutableLiveData<Boolean>()
    private var _resultData = MutableLiveData<List<User>>()

    val isLoading : LiveData<Boolean> = _isLoading
    val resultData : LiveData<List<User>> = _resultData

    fun searchData(keywords : String ){
        _isLoading.value = true
        val client = ApiConfig.ApiService().searchUser(keywords)
        client.enqueue(object  : Callback<SearchResult> {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null ) {
                    val responseContent = response.body()
                    if(responseContent?.items !=null) {
                            _resultData.value = responseContent.items as List<User>
                    }
                }
            }
            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG,"On Failure : ${t.message}")
            }
        })
    }
    companion object {
        private const val TAG = "ExploreViewModel"
    }
}