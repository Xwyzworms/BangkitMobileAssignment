package com.example.submission2_ezpz.source_data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.submission2_ezpz.data.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {


    @Query("SELECT * FROM users WHERE favorite = 1")
    fun getFavorites() : Flow<List<User>>

    @Query("SELECT * FROM users ORDER BY username ASC")
    fun getUser() : Flow<List<User>>

    @Insert
    fun insertFavorites(user : User)

    @Query("DELETE FROM users WHERE username=:username AND :favorite=1")
    fun deleteFavorites(username : String, favorite : Boolean)

    @Query("DELETE FROM users WHERE favorite=1")
    fun deleteAllFavorites()

    @Query("SELECT EXISTS(SELECT * FROM users WHERE username=:username AND favorite= 1)")
    fun isUserFavorites(username:String, favorite: Boolean) : Boolean


}