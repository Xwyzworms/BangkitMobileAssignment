package com.example.sec2_latihanaplikasi8

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.sec2_latihanaplikasi8.adapters.ReviewAdapter
import com.example.sec2_latihanaplikasi8.databinding.ActivityMainBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvReview.layoutManager = layoutManager
        binding.rvReview.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

        findRestaurant()

        binding.btnSend.setOnClickListener {
            postReview(binding.edReview.text.toString())
            val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(it.windowToken,0)
        }

    }

    private fun postReview(review : String ) {
        showLoading(true)
        val client =Apiconfig.getApiService()
            .postReview(RESTAURANT_ID,"Primitif",review)
            .enqueue(object : Callback<PostReviewResponse> {
                override fun onResponse(
                    call: Call<PostReviewResponse>,
                    response: retrofit2.Response<PostReviewResponse>
                ) {
                    showLoading(false)
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null ){
                        setReviewData(responseBody.customerReviews)
                    }
                    else {
                        Log.e(TAG, "onFailure : ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<PostReviewResponse>, t: Throwable) {
                    showLoading(false)
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }
    private fun findRestaurant() {
        showLoading(true)
        val client = Apiconfig.getApiService().getRestaurant(RESTAURANT_ID)
        client.enqueue(object : Callback<Response> {
            override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
                showLoading(false)
                if(response.isSuccessful) {
                    val responseBody: Response? = response.body()
                    if (responseBody != null) {
                        setRestaurantData(responseBody.restaurant)
                        setReviewData(responseBody.restaurant?.customerReviews )
                    }
                }
                else {

                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Response>, t: Throwable) {

                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })

    }

    private fun setRestaurantData(restaurant: Restaurant?) {
        if (restaurant != null) {
            binding.tvTitle.text = restaurant.name
            binding.tvDescription.text = restaurant.description
            Glide.with(this@MainActivity)
                .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
                .into(binding.ivPicture)
        }
    }

    private fun setReviewData(customerReviews: List<CustomerReviewsItem?>?) {
        if(customerReviews != null ) {
            val listReview = ArrayList<String>()
            for (customer in customerReviews) {
                if(customer != null ) {
                    listReview.add("""
                    ${customer.review} 
                    -${customer.name}
                    """.trimIndent()
                    )
                }
            }
            val adapter = ReviewAdapter(listReview)
            binding.rvReview.adapter = adapter
            binding.edReview.setText("")
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = ProgressBar.VISIBLE
        }
        else {
            binding.progressBar.visibility = ProgressBar.INVISIBLE
        }
    }


    companion object {
        private const val TAG = "MainActivity"
        private const val RESTAURANT_ID = "rqdv5juczeskfw1e867"
    }

}