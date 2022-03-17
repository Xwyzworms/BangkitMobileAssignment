package com.example.submission2_ezpz.source_data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class UserEntity(

    @field:ColumnInfo(name="username")
    @field:PrimaryKey
    val username : String,

    @field:ColumnInfo(name="avatar_url")
    val avatarUrl : String,

    @field:ColumnInfo(name="url")
    val githubUrl : String,

    @field:ColumnInfo(name="favorite")
    val isFavorite : Boolean
)