package com.example.submission2_ezpz.source_data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.data.UserOwner
import com.example.submission2_ezpz.source_data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM users WHERE favorite = 1")
    fun getFavorites() : LiveData<List<UserEntity>>

    @Query("SELECT * FROM users ORDER BY RANDOM() ")
    fun getUsers() : LiveData<List<UserEntity>>

    @Query("SELECT EXISTS( SELECT * FROM users WHERE username=:username)")
    fun isUserExists(username: String) : Boolean

    @Query("SELECT * FROM users WHERE username=:username")
    fun getUser(username : String) : UserEntity

    @Update
    fun updateFavorite(user : UserEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorites(users : List<UserEntity>)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(user : UserEntity)

    @Delete
    suspend fun delete(user:UserEntity)


    @Query("SELECT * FROM users where username LIKE '%'||:keyword || '%'")
    fun getFiltered(keyword:String ) : LiveData<List<UserEntity>>
    @Query("DELETE FROM users WHERE favorite=0")
    suspend fun deleteAllNoFavorites()

    @Query("SELECT EXISTS(SELECT * FROM users WHERE username=:username AND favorite= 1)")
    suspend fun isUserFavorites(username:String) : Boolean


}