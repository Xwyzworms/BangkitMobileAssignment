package com.example.sec3_latihanaplikasi19_repository.di

import android.content.Context
import com.example.sec3_latihanaplikasi19_repository.data.NewsRepository
import com.example.sec3_latihanaplikasi19_repository.data.local.room.NewsDatabase
import com.example.sec3_latihanaplikasi19_repository.data.remote.retrofit.ApiConfig

object Injection {
    fun provideRepository(context : Context) : NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        return NewsRepository.getInstance(apiService, dao)
    }
}