package com.example.sec3_latihanaplikasi19_repository.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sec3_latihanaplikasi19_repository.data.NewsRepository
import com.example.sec3_latihanaplikasi19_repository.di.Injection
import java.lang.IllegalArgumentException

class ViewModelFactory private constructor(private val newsRepository: NewsRepository) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)){
            return NewsViewModel(newsRepository) as T
        }
        throw IllegalArgumentException("Unknwon View Model class ${modelClass.simpleName}")
    }

    companion object {
        @Volatile
        private var instance : ViewModelFactory? = null
        fun getInstance(context: Context) : ViewModelFactory {
            return instance ?: synchronized(this) {
                 instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
        }
    }
}