package com.example.sec2_latihanaplikasi7

import com.example.sec2_latihanaplikasi7.data_class.Random
import com.example.sec2_latihanaplikasi7.data_class.RandomSecond
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface  {

    @GET("random")
    fun getRandom() : Call<Random>

    @GET("list")
    fun getListRandom() : Call<ArrayList<RandomSecond>>

}
