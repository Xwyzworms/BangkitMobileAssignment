package com.example.sec2_latihanaplikasi7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sec2_latihanaplikasi7.adapters.QouteAdapter
import com.example.sec2_latihanaplikasi7.data_class.RandomSecond
import com.example.sec2_latihanaplikasi7.databinding.ActivityListQoutesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListQoutesActivity : AppCompatActivity() {
    private lateinit var binding : ActivityListQoutesBinding
    private lateinit var recyclerView :RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListQoutesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        recyclerView = binding.listQuotes
        recyclerView.adapter = QouteAdapter(arrayListOf(RandomSecond("2","2","2","2",2.2F)))

        val api : ApiInterface = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MainActivity.BASE_URL)
            .build().create(ApiInterface::class.java)

        api.getListRandom().enqueue(object : Callback<ArrayList<RandomSecond>> {
            override fun onResponse(
                call: Call<ArrayList<RandomSecond>>,
                response: Response<ArrayList<RandomSecond>>
            ) {
                if(response.code() == 200) {
                    binding.progressBar.visibility = View.INVISIBLE
                    Handler(Looper.getMainLooper()).post{
                        recyclerView.adapter = QouteAdapter(response.body() as ArrayList<RandomSecond>)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<RandomSecond>>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })


    }
}