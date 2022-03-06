package com.example.sec2_latihanaplikasi9

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){
    var result : Int = 0

    fun calculate(width : String, height : String, length: String )
    {
        result = width.toInt() * height.toInt() * length.toInt()
    }
}