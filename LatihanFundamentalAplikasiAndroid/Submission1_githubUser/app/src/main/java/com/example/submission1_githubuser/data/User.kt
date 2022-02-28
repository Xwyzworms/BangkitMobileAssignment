package com.example.submission1_githubuser.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    var username : String,
    var name : String,
    var avatar : Int,
    var followers : String,
    var following : String,
    var company : String,
    var repository : String,
    var location : String
        ) : Parcelable