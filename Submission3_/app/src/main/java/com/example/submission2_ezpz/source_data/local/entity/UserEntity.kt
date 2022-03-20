package com.example.submission2_ezpz.source_data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "users")
class UserEntity(

    @field:ColumnInfo(name="username")
    @field:PrimaryKey
    var username : String,

    @field:ColumnInfo(name="avatar_url")
    var avatarUrl : String,

    @field:ColumnInfo(name="url")
    var githubUrl : String,

    @field:ColumnInfo(name="favorite")
    var isFavorite : Boolean
) :Parcelable