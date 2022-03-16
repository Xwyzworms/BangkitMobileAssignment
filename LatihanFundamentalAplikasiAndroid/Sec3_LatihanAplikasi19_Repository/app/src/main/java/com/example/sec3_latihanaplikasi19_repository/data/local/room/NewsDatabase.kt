package com.example.sec3_latihanaplikasi19_repository.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sec3_latihanaplikasi19_repository.data.local.entity.NewsEntity



@Database(entities = [NewsEntity::class], version=1, exportSchema = false)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao() : NewsDao

    companion object {
        @Volatile
        private var INSTANCE : NewsDatabase? = null

        @JvmStatic
        fun getInstance(context : Context) : NewsDatabase {
            return INSTANCE ?: synchronized(this) {
               return INSTANCE ?: Room.databaseBuilder(context.applicationContext,NewsDatabase::class.java,"news.db").build()
            }
        }
    }

}