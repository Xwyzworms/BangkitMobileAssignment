package com.example.sec2_latihanaplikasi11

import android.util.Log
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback

class MainActivityViewModel : ViewModel() {



    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant : LiveData<Restaurant> = _restaurant

    private val _listReview = MutableLiveData<List<CustomerReviewsItem?>>()
    val listReview : LiveData<List<CustomerReviewsItem?>> = _listReview

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackBarText = _snackbarText

    init {
        findRestaurant()
    }

    private fun findRestaurant() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)
        client.enqueue(
            object : Callback<Response> {
                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    _isLoading.value = false
                    if(response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d(TAG, response.body()?.restaurant?.customerReviews.toString())
                        if(responseBody != null) {
                            _restaurant.value = responseBody.restaurant
                            if(responseBody.restaurant?.customerReviews != null ) {
                                _listReview.value = responseBody.restaurant.customerReviews
                            }
                        }
                    }
                    else {
                        Log.d (MainActivity.TAG, response.message())
                    }
                }
                override fun onFailure(call: Call<Response>, t: Throwable) {
                    _isLoading.value = false
                    Log.d(MainActivity.TAG, t.message.toString())
                }
            }
        )
    }

    fun postReview(review : String ) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postReview(MainActivity.RESTAURANT_ID,"SovietMarch",review)
        client.enqueue(
            object : Callback<PostReviewResponse> {
                override fun onResponse(
                    call: Call<PostReviewResponse>,
                    response: retrofit2.Response<PostReviewResponse>
                ) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null && response.isSuccessful) {
                        _listReview.value = responseBody.customerReviews
                        _snackbarText.value = Event(responseBody.message)
                    }
                    else {
                        Log.d(MainActivity.TAG, "onFailure : ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
                    Log.d(MainActivity.TAG, "onFailure : ${t.message.toString()}")
                }
            }
        )
    }

    companion object {
        const val TAG = "MainActivityViewModel"
        const val  RESTAURANT_ID = "rqdv5juczeskfw1e867"
    }


}
