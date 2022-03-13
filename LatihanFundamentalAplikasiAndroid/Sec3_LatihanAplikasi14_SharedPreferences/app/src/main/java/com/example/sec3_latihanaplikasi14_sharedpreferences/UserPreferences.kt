package com.example.sec3_latihanaplikasi14_sharedpreferences

import android.content.Context

internal class UserPreferences(context : Context) {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val AGE = "age"
        private const val PHONE_NUMBER = "phone"
        private const val LOVE_MU = "isLove"
        private const val default_string = ""
        private const val default_int = 0
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)


    fun setUser(value : User) {
        val editor =preferences.edit()
        editor.putString(NAME, value.name)
        editor.putString(EMAIL, value.email)
        editor.putString(PHONE_NUMBER, value.phoneNumber)
        editor.putInt(AGE, value.age)
        editor.putBoolean(LOVE_MU, value.isLove)
        editor.apply()
    }

    fun getUser() : User {
        val user = User()
        user.name = preferences.getString(NAME, default_string)
        user.email = preferences.getString(EMAIL, default_string)
        user.age = preferences.getInt(AGE, default_int)
        user.phoneNumber = preferences.getString(PHONE_NUMBER, default_string)
        user.isLove  = preferences.getBoolean(LOVE_MU, false)
        return user
    }


}