package com.example.sec3_latihanaplikasi14_sharedpreferences

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    var name : String? = null,
    var email : String? = null,
    var age : Int = 0,
    var phoneNumber : String? = null,
    var isLove : Boolean = false
        ) : Parcelable