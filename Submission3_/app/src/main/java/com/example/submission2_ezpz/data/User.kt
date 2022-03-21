package com.example.submission2_ezpz.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(

	@field:SerializedName("login")
	var username: String ,

	@field:SerializedName("avatar_url")
	var avatarUrl: String,

	@field:SerializedName("url")
	var githubUrl: String,

	var isFavorite : Boolean? = false
) : Parcelable
