package com.example.submission2_ezpz.source_data.local.networks

import com.example.submission2_ezpz.BuildConfig
import com.example.submission2_ezpz.data.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

 companion object{
  const val TOKEN = BuildConfig.API_KEY
 }

 @GET("/users")
 @Headers("Authorization: token $TOKEN")
 fun getUsers() : Call<List<User>>

 @GET("/search/users")
 @Headers("Authorization: token $TOKEN")
 fun searchUser(@Query("q") query : String) : Call<SearchResult>

 @GET("/users/{username}")
 @Headers("Authorization: token $TOKEN")
 fun getDetailUser(@Path("username") username : String ) : Call<UserOwner>

 @GET("/users/{username}/following")
 @Headers("Authorization: token $TOKEN")
 fun getFollowing(@Path("username") username : String) : Call<List<User>>

 @GET("/users/{username}/followers")
 @Headers("Authorization: token $TOKEN")
 fun getFollowers(@Path("username") username: String) : Call<List<User>>


}