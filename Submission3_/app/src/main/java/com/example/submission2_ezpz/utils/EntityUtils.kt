package com.example.submission2_ezpz.utils

import com.example.submission2_ezpz.data.User
import com.example.submission2_ezpz.source_data.local.entity.UserEntity

object EntityUtils {
    fun convertUserToEntity(user : User, isFavorite: Boolean) : UserEntity{
        val userFav = UserEntity(user.username,user.avatarUrl,user.githubUrl, isFavorite )
        return userFav
    }
}