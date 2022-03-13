package com.example.sec2_latihanaplikasi7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.sec2_latihanaplikasi7.data_class.Random
import com.example.sec2_latihanaplikasi7.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getRandomQoute()

        binding.btnAllQuotes.setOnClickListener {
            startActivity(Intent(this@MainActivity, ListQoutesActivity::class.java))
        }

    }


    private fun getRandomQoute() {
        binding.progressBar.visibility = View.VISIBLE
        val api : ApiInterface = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build().create(ApiInterface::class.java)

        api.getRandom().enqueue(object : Callback<Random> {
            override fun onResponse(call: Call<Random>, response: Response<Random>) {
                binding.progressBar.visibility = View.INVISIBLE

                val result = response.body()
                if(result!= null) {
                    val qoute = result.en
                    val author = result.author

                    binding.tvAuthor.text = author
                    binding.tvQuote.text = qoute
                }
            }

            override fun onFailure(call: Call<Random>, t: Throwable) {
                binding.progressBar.visibility = View.INVISIBLE

                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_LONG).show()
            }

        })

    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        const val BASE_URL = "https://quote-api.dicoding.dev/"


    }
}