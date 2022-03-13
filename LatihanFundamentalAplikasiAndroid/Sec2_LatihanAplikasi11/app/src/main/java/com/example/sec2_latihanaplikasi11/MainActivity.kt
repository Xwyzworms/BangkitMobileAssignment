package com.example.sec2_latihanaplikasi11

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.sec2_latihanaplikasi11.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : ReviewAdapter

    companion object {
        const val RESTAURANT_ID = "rqdv5juczeskfw1e867"
        const val TAG : String = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.rvReview.layoutManager = layoutManager


        val mainActivityViewModel = ViewModelProvider(this@MainActivity,ViewModelProvider.NewInstanceFactory()).get(MainActivityViewModel::class.java)


        // SettingViewModel
        mainActivityViewModel.restaurant.observe(this) {
            setRestaurantData(it)
        }
        mainActivityViewModel.isLoading.observe(this) {
            showLoading(it)
        }
        mainActivityViewModel.listReview.observe(this) {
            setReviewData(it)
        }
        mainActivityViewModel.snackBarText.observe(this) {
            it.getContentIfNotHandled().let { snackBar_text ->
                Snackbar.make(
                    binding.root,
                    snackBar_text.toString(),
                    Snackbar.LENGTH_SHORT
                ).show()
            }

        }

        binding.btnSend.setOnClickListener { it ->
            mainActivityViewModel.postReview(binding.edReview.text.toString())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0 )
        }


    }

    private fun setRestaurantData(restaurant : Restaurant?) {
        if(restaurant != null) {
            binding.tvTitle.text = restaurant.name
            binding.tvDescription.text = restaurant.description

            Glide.with(this@MainActivity)
                .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
                .into(binding.ivPicture)
        }
    }

    private fun setReviewData(customerReviews : List<CustomerReviewsItem?>?) {
        if(customerReviews != null) {
            Log.d(TAG,customerReviews.toString())
            val listReview = ArrayList<String>()
            for (customer in customerReviews) {
                if (customer != null) {
                    listReview.add("""
                        ${customer.review}
                        -${customer.name}
                    """.trimIndent())
                }
            }

            adapter = ReviewAdapter(listReview)
            binding.rvReview.adapter = adapter
            binding.edReview.setText("")
        }
    }


    private fun showLoading(state : Boolean) {
        if(state) {
            binding.progressBar.visibility = ProgressBar.VISIBLE
        }
        else {
            binding.progressBar.visibility = ProgressBar.INVISIBLE
        }
    }



}