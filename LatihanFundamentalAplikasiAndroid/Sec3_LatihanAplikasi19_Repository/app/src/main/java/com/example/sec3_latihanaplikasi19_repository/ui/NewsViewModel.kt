package com.example.sec3_latihanaplikasi19_repository.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sec3_latihanaplikasi19_repository.data.NewsRepository
import com.example.sec3_latihanaplikasi19_repository.data.local.entity.NewsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel(){

    fun getHeadLineNews() = newsRepository.getHeadLineNews()
    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()

    fun saveNews(news : NewsEntity) {
        viewModelScope.launch {
            newsRepository.setNewsBookmark(news, true)
        }
    }

    fun deleteNews(news : NewsEntity) {
        viewModelScope.launch {
            newsRepository.setNewsBookmark(news, false)
        }
    }

}