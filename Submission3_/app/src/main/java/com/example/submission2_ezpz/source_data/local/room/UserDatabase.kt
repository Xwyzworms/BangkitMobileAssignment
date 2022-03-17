package com.example.submission2_ezpz.source_data.local.room

import android.content.Context
import android.service.autofill.UserData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.submission2_ezpz.source_data.local.entity.UserEntity


@Database(entities=[UserEntity::class], version=1)
abstract class UserDatabase () : RoomDatabase() {

    abstract fun userDao() : UserDao

    companion object {
        @Volatile
        private var INSTANCE : UserDatabase? =null

        @JvmStatic
        fun getDatabaseInstance(context : Context) : UserDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context, UserDatabase::class.java, "user.db").build()
                INSTANCE = instance
                instance
            }
        }
    }
}