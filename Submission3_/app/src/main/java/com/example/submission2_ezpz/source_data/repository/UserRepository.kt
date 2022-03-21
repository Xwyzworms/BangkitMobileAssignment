package com.example.submission2_ezpz.source_data.repository

import android.util.Log
import androidx.lifecycle.*
import com.example.submission2_ezpz.data.SearchResult
import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.data.UserOwner
import com.example.submission2_ezpz.source_data.Result
import com.example.submission2_ezpz.source_data.local.entity.UserEntity
import com.example.submission2_ezpz.source_data.local.networks.ApiConfig
import com.example.submission2_ezpz.source_data.local.networks.ApiInterface
import com.example.submission2_ezpz.source_data.local.room.UserDao
import com.example.submission2_ezpz.source_data.local.setting_preference.SettingPreferences
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor (
    private val apiService : ApiInterface,
    private val userDao: UserDao,
    private val userPreferences: SettingPreferences) {


    fun getLocalData(): LiveData<Result<List<UserEntity>>> = liveData {
        try {

            Log.d(TAG, "localData")
            emit(Result.Loading)
            prepareRemoteUsersData()
        } catch (e: Exception) {
            Log.d(TAG, "prepareRemoteUsersData: ${e.message}")
            emit(Result.Error(e.message.toString()))
        }
        emitSource(userDao.getUsers().map { Result.Success(it) })
    }

    fun getUserInfoRemote(username : String) : LiveData<Result<UserOwner>> = liveData {
        try {
            emit(Result.Loading)
            val response : UserOwner = ApiConfig.ApiService().getDetailUser(username)
            val isFavorited = userDao.isUserFavorites(response.login as String )
            val userEntity = UserEntity(response.login, response.avatarUrl as String, response.url as String, isFavorited)

            userDao.deleteAllNoFavorites()
            userDao.insertFavorite(userEntity)

            val returnedUser : LiveData<UserOwner> = MutableLiveData(response)
            withContext(Dispatchers.Main) {
                emitSource(returnedUser.map { Result.Success(it) })
            }
        }
        catch (e : Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getUserInfoLocal(username : String) : LiveData<Result<UserEntity>> = liveData {
        try {
            emit(Result.Loading)
            withContext(Dispatchers.IO) {
                val response = userDao.getUser(username)
                val returned: LiveData<UserEntity> = MutableLiveData(response)
                withContext(Dispatchers.Main) {
                    emitSource(returned.map { Result.Success(it) })
                }
            }
        }
        catch (e : Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getLocalSearchCoroutines(keywords: String): LiveData<Result<List<UserEntity>>> = liveData {
        try {
            emit(Result.Loading)
            val response = ApiConfig.ApiService().getUsersCoroutines(keywords)
            val users = response.items
            if (users != null) {
                val userEntity = users.map {
                    val isfavor = userDao.isUserFavorites(it?.username as String)
                    UserEntity(it.username, it.avatarUrl, it.githubUrl, isfavor)
                }
                userDao.deleteAllNoFavorites()
                userDao.insertFavorites(userEntity)
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
        val dataReturned: LiveData<Result<List<UserEntity>>> =
            userDao.getFiltered(keywords).map { Result.Success(it) }
        emitSource(dataReturned)
    }


    suspend fun removeUser(user: UserEntity) {
        userDao.delete(user)
    }

    fun getFollowers(username: String?): LiveData<Result<List<UserEntity>>> = liveData {
        try {
            emit(Result.Loading)
            if (username != null && username.isNotEmpty()) {
                val listUser = ApiConfig.ApiService().getFollowers(username)
                val userEntities: List<UserEntity> =
                    listUser.map {
                        val isFavorited = userDao.isUserFavorites(it.username)
                        UserEntity(
                            it.username,
                            it.avatarUrl,
                            it.githubUrl,
                            isFavorited
                        )
                    }

                val dataReturned : LiveData<List<UserEntity>> = MutableLiveData(userEntities)
                emitSource(dataReturned.map { Result.Success(it) })
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowing(username: String) : LiveData<Result<List<UserEntity>>> = liveData {
        try {
            emit(Result.Loading)
            val response = ApiConfig.ApiService().getFollowing(username)
            val userEntities  : LiveData<List<UserEntity>> = MutableLiveData(response.map {
                val isFavorite = userDao.isUserFavorites(it.username)
                UserEntity(it.username, it.avatarUrl, it.githubUrl, isFavorite)
            })
            withContext(Dispatchers.Main) {
                emitSource(userEntities.map{Result.Success(it)})
            }
        }catch (e : Exception) {
            emit(Result.Error(e.message.toString()))
        }
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
                        userDao.deleteAllNoFavorites()
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