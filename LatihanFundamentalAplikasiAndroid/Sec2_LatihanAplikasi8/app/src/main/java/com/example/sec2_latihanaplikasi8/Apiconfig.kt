package com.example.sec2_latihanaplikasi8

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class Apiconfig {
    companion object {
        private const val BASE_URL : String = "https://restaurant-api.dicoding.dev/"
        fun getApiService() : ApiService
        {
            val loggingInterceptor =
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                }
                else
                {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
                }
            val client = OkHttpClient.Builder().apply {
                addInterceptor(loggingInterceptor)
            }.build()
            val retrofit = Retrofit.Builder().apply {
                baseUrl(BASE_URL)
                addConverterFactory(GsonConverterFactory.create())
                client(client)
            }.build()
            return retrofit.create(ApiService::class.java)
        }

    }
}