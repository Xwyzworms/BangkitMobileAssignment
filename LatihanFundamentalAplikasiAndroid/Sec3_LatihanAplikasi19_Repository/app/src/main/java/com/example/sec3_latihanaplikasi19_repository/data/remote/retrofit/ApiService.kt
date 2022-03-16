package com.example.sec3_latihanaplikasi19_repository.data.remote.retrofit

import com.example.sec3_latihanaplikasi19_repository.data.remote.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines?country=id&category=science")
    suspend fun getNews(@Query("apiKey") apiKey : String) : NewsResponse
}