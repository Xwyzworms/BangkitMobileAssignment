package com.example.submission2_ezpz.source_data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.source_data.Result
import com.example.submission2_ezpz.source_data.local.entity.UserEntity
import com.example.submission2_ezpz.source_data.local.networks.ApiConfig
import com.example.submission2_ezpz.source_data.local.networks.ApiInterface
import com.example.submission2_ezpz.source_data.local.room.UserDao
import com.example.submission2_ezpz.source_data.local.setting_preference.SettingPreferences
import com.example.submission2_ezpz.utils.EntityUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor (
    private val apiService : ApiInterface,
    private val userDao: UserDao,
    private val userPreferences: SettingPreferences) {


    fun getLocalData() : LiveData<Result<List<UserEntity>>> = liveData {
        try {
            emit(Result.Loading)
            prepareRemoteUsersData()
        }
        catch (e : Exception) {
            Log.d(TAG, "prepareRemoteUsersData: ${e.message}")
            emit(Result.Error(e.message.toString()))
        }
        emitSource(userDao.getUsers().map { Result.Success(it)} )
    }
    private fun prepareRemoteUsersData() {
        val client = ApiConfig.ApiService().getUsers()
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val responseContent = response.body()
                if (responseContent != null) {
                    CoroutineScope(Dispatchers.IO).launch {
                        val userEntityList = responseContent.map {
                            val isFavorited = userDao.isUserFavorites(it.username)
                            UserEntity(it.username, it.avatarUrl, it.githubUrl,isFavorited)
                        }
                        userDao.deleteAllFavorites()
                        userDao.insertFavorites(userEntityList)

                        }
                    }

                }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d(TAG, "OnFailure ${t.message}")
            }
        })
    }

    fun getCurrentTheme() : LiveData<Boolean>{
        return userPreferences.getThemeSettings().asLiveData()
    }

    fun getFavorites() : LiveData<Result<List<UserEntity>>> {
        return userDao.getFavorites().map {
            Result.Success(it)
        }
    }

    suspend fun setFavorites(user : UserEntity, favoriteState : Boolean) {

        CoroutineScope(Dispatchers.IO).launch{
            val userTemp = user
            userTemp.isFavorite = favoriteState
            if (userDao.isUserExists(userTemp.username)) {
                userDao.updateFavorite(userTemp)
            } else {
                userDao.insertFavorite(userTemp)
            }
        }
    }

    companion object {
        private val TAG : String = UserRepository::class.simpleName as String
        @Volatile
        private var INSTANCE : UserRepository? = null

        fun getInstance(
             apiService: ApiInterface,
             userDao : UserDao
            ,userPreferences : SettingPreferences ) : UserRepository
        {
            return INSTANCE ?: synchronized(this){
                val instance = UserRepository(apiService,userDao,userPreferences)
                INSTANCE = instance
                instance
            }
        }
    }
}