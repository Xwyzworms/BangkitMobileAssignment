package com.example.sec3_latihanaplikasi19_repository.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sec3_latihanaplikasi19_repository.data.local.entity.NewsEntity

@Dao
interface NewsDao {

 @Query("SELECT * FROM news ORDER BY publishedAt DESC")
 fun getNews() : LiveData<List<NewsEntity>>

 @Query("SELECT * FROM news where bookmarked = 1")
 fun getBookmarkedNews() : LiveData<List<NewsEntity>>

 @Query("DELETE FROM news WHERE bookmarked = 0")
 suspend fun deleteAll()

 @Update
 suspend fun updateNews(news : NewsEntity)

 @Insert(onConflict = OnConflictStrategy.IGNORE)
 suspend fun insertNews(news : List<NewsEntity>)

 @Query("SELECT " +
         "EXISTS(" +
         "SELECT * " +
         "FROM news " +
         "WHERE title = :title AND bookmarked = 1)")
 suspend fun isNewsBookmarked(title: String) : Boolean


}