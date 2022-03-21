package com.example.sec3_latihanaplikasi19_repository.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.viewbinding.BuildConfig
import com.example.sec3_latihanaplikasi19_repository.data.local.entity.NewsEntity
import com.example.sec3_latihanaplikasi19_repository.data.local.room.NewsDao
import com.example.sec3_latihanaplikasi19_repository.data.remote.response.ArticlesItem
import com.example.sec3_latihanaplikasi19_repository.data.remote.retrofit.ApiService

class NewsRepository private constructor(
    private val apiService : ApiService,
    private val newsDao: NewsDao
){
    fun getHeadLineNews() : LiveData<Result<List<NewsEntity>>> =  liveData {
        emit(Result.Loading)
        try {
            val response =  apiService.getNews("db874e166f4c473e9132d19a45135274")
            val articles : List<ArticlesItem> = response.articles
            val newsList : List<NewsEntity> = articles.map { article ->
                val isBookmarked = newsDao.isNewsBookmarked(article.title)
                NewsEntity(
                    article.title,
                    article.publishedAt,
                    article.urlToImage,
                    article.url,
                    isBookmarked
                )
            }
            newsDao.deleteAll()
            newsDao.insertNews(newsList)
        }catch (e : Exception) {
            Log.d(TAG,"GetHeadlineNews : ${e.message.toString()}")
            emit(Result.Error(e.message.toString()))
        }
        val localData : LiveData<Result<List<NewsEntity>>> = newsDao.getNews().map {
            Result.Success(it)
        }
        emitSource(localData)
    }

    fun getBookmarkedNews() : LiveData<Result<List<NewsEntity>>> = liveData {
        val bookmarekedNews : LiveData<Result<List<NewsEntity>>> = newsDao.getBookmarkedNews().map {
            Result.Success(it)
        }
        emitSource(bookmarekedNews)
    }

    suspend fun setNewsBookmark(news : NewsEntity, bookmarkState : Boolean) {
        news.isBookmarked = bookmarkState
        newsDao.updateNews(news)
    }

    companion object {
        const val TAG = "NewsRepository"
        @Volatile
        private var instance  : NewsRepository ? =null
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao
        ) : NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService,newsDao)
            }.also {
                    thisInstance -> instance = thisInstance
            }

    }

}