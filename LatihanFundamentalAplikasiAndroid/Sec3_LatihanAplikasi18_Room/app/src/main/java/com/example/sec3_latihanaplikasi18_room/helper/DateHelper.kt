package com.example.sec3_latihanaplikasi18_room.helper

import java.text.SimpleDateFormat
import java.util.*

object DateHelper {

    fun getCurrentDate() : String {
        val dateFormat = SimpleDateFormat("yyyy//MM//dd HH::mm::ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}